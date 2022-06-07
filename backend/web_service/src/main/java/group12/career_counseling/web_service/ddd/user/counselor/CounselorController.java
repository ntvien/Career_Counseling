package group12.career_counseling.web_service.ddd.user.counselor;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.user.counselor.request.LoginRequest;
import group12.career_counseling.web_service.ddd.user.counselor.request.RegisterRequest;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CounselorController {
    @Autowired
    private ICounselorService counselorService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    public void register(RoutingContext routingContext) {
        try {
            RegisterRequest registerRequest = JSON.parseObject(routingContext.getBodyAsString(), RegisterRequest.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(counselorService.createCounselor(registerRequest)));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void login(RoutingContext routingContext) {
        try {
            //implement login here
            LoginRequest request = JSON.parseObject(routingContext.getBodyAsString(), LoginRequest.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(counselorService.authenticate(request)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);

        }
    }

    public void getProfile(RoutingContext routingContext) {
        try {
            String counselor = routingContext.user().principal().getString("userId");
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(counselorService.getProfile(counselor)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
