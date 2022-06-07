package group12.career_counseling.web_service.ddd.user.student;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.user.student.request.AuthenticationRequest;
import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.ddd.user.student.request.StudentRequest;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.utils.enumeration.authentication.AttributeUserJWT;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StudentController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IStudentService studentService;

    public void register(RoutingContext routingContext) {
        try {
            StudentRequest studentRequest = JSON.parseObject(routingContext.getBodyAsString(), StudentRequest.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(studentService.createStudent(studentRequest)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void login(RoutingContext routingContext) {
        try {
            AuthenticationRequest authenticationRequest = JSON.parseObject(routingContext.getBodyAsString(), AuthenticationRequest.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(studentService.authenticate(authenticationRequest)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);

        }
    }

    public void getProfile(RoutingContext routingContext) {
        try {
            String student_id = routingContext.user().principal().getString("userId");
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(studentService.getStudentById(student_id)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);

        }
    }


}
