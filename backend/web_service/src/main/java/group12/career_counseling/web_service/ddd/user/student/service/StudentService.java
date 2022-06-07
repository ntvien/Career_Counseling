package group12.career_counseling.web_service.ddd.user.student.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.repository.IStudentRepository;
import group12.career_counseling.web_service.ddd.user.student.request.AuthenticationRequest;
import group12.career_counseling.web_service.ddd.user.student.request.ChangePasswordRequest;
import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.ddd.user.student.request.StudentRequest;
import group12.career_counseling.web_service.utils.MyStringUtils;
import group12.career_counseling.web_service.utils.crypto.SHA512;
import group12.career_counseling.web_service.vertx.rest.authentication.JWTClaim;
import group12.career_counseling.web_service.vertx.rest.authentication.MyJWTAuthentication;
import group12.career_counseling.web_service.vertx.rest.authentication.AuthenticationResponse;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private MyJWTAuthentication jwtAuthentication;


    @Override
    public Student getStudentById(String id) throws HttpException {
        return this.studentRepository
                .getById(id)
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Boolean updateStatus(String id, String status, long timeAt) {
        return this.studentRepository.update(id, Student
                .builder()
                .lastStatus(Student.Status
                        .builder()
                        .status(status)
                        .timeAt(timeAt)
                        .build()).build()).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Student createStudent(StudentRequest studentRequest) throws HttpException {
        if (StringUtils.isAnyBlank(studentRequest.getUserName(), studentRequest.getFullName(), studentRequest.getPassword())) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.REQUIRED_FIELDS_ARE_NULL);
        }
        Document query = new Document()
                .append("userName", studentRequest.getUserName());

        studentRepository.getByQuery(query).ifPresent((value) -> {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.USERNAME_EXISTED);
        });
        Student student = objectMapper.convertValue(studentRequest, Student.class);
        student.setFullNameUnsigned(MyStringUtils.removeUnicode(student.getFullName()));
        student.setPassword(SHA512.valueOf(studentRequest.getPassword()));
        return studentRepository.insert(student).orElseThrow(() ->
                new HttpException(StatusCodes.INTERNAL_SERVER_ERROR)
        );
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws HttpException {
        if (StringUtils.isAnyBlank(authenticationRequest.getPassword(), authenticationRequest.getPassword())) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.REQUIRED_FIELDS_ARE_NULL);
        }
        Document query = new Document()
                .append("userName", authenticationRequest.getUserName());
        Student student = studentRepository.getByQuery(query).orElseThrow(() ->
                new HttpException(StatusCodes.UNAUTHORIZED, ExceptionMessages.INCORRECT_USERNAME)
        );
        if (!SHA512.valueOf(authenticationRequest.getPassword()).equals(student.getPassword())) {
            throw new HttpException(StatusCodes.UNAUTHORIZED, ExceptionMessages.INCORRECT_PASSWORD);
        }
        JWTClaim jwtClaim = JWTClaim.builder()
                .userId(student.get_id().toHexString())
                .groups(List.of(Roles.STUDENT))
                .build();

        return jwtAuthentication.generateToken(jwtClaim);
    }


}
