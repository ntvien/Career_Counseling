package group12.career_counseling.web_service.ddd.user;

import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.vertx.rest.authentication.AuthenticationResponse;
import group12.career_counseling.web_service.vertx.rest.authentication.MyJWTAuthentication;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ICounselorService counselorService;
    @Autowired
    private MyJWTAuthentication jwtAuthentication;
    @Override
    public User getUserProfile(String userId, String role) {
        if (role.equals(Roles.STUDENT)) {
            Student student = this.studentService.getStudentById(userId);
            return User.builder()
                    ._id(student.get_id())
                    .fullName(student.getFullName())
                    .userName(student.getUserName())
                    .role(Roles.STUDENT)
                    .build();
        } else {
            Counselor counselor = this.counselorService.getCounselorById(userId);
            return User.builder()
                    ._id(counselor.get_id())
                    .email(counselor.getEmail())
                    .universityId(counselor.getUniversityId())
                    .phone(counselor.getPhone())
                    .role(Roles.COUNSELOR)
                    .build();
        }
    }
    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws HttpException {
        if (StringUtils.isBlank(refreshTokenRequest.getRefreshToken())) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.REQUIRED_FIELDS_ARE_NULL);
        }
        return jwtAuthentication.refreshToken(refreshTokenRequest.getRefreshToken()).orElseThrow(() ->
                new HttpException(StatusCodes.UNAUTHORIZED, ExceptionMessages.INVALID_TOKEN)
        );
    }
}
