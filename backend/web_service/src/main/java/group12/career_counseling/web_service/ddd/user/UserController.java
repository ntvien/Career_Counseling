package group12.career_counseling.web_service.ddd.user;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IUserService userService;

    public void getUserProfile(RoutingContext routingContext) {
        try {
            String userId = routingContext.user().principal().getString(ClaimFieldName.USER_ID);
            String role = objectMapper.readValue(routingContext.user().principal().getString(ClaimFieldName.GROUPS), new TypeReference<List<String>>() {
            }).get(0);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(userService.getUserProfile(userId,role)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
    public void refreshToken(RoutingContext routingContext) {
        try {
            RefreshTokenRequest refreshTokenRequest = JSON.parseObject(routingContext.getBodyAsString(), RefreshTokenRequest.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(userService.refreshToken(refreshTokenRequest)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
