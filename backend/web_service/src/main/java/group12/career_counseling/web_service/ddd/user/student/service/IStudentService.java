package group12.career_counseling.web_service.ddd.user.student.service;

import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.request.AuthenticationRequest;
import group12.career_counseling.web_service.ddd.user.student.request.ChangePasswordRequest;
import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.ddd.user.student.request.StudentRequest;
import group12.career_counseling.web_service.vertx.rest.authentication.AuthenticationResponse;
import io.vertx.ext.web.handler.HttpException;

public interface IStudentService {
    Student getStudentById(String id);
    Boolean updateStatus(String id, String status, long timeAt);

    Student createStudent(StudentRequest studentRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);


}
