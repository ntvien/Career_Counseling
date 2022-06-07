package group12.career_counseling.web_service.ddd.notification.user_device;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.notification.user_device.service.IUserDeviceService;
import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserDeviceController {
    @Autowired
    private IUserDeviceService userDeviceService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;
    public void addUserDevice(RoutingContext routingContext) {
        try {
            String  token = routingContext.getBodyAsJson().getString("token");
            String user_id = routingContext.user().principal().getString(ClaimFieldName.USER_ID);
            String role = objectMapper.readValue(routingContext.user().principal().getString(ClaimFieldName.GROUPS), new TypeReference<List<String>>() {
            }).get(0);
            this.userDeviceService.AddUserDevice(user_id,role,token);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8");
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
    public void removeUserDevice(RoutingContext routingContext) {
        try {
            String  token = routingContext.getBodyAsJson().getString("token");
            String user_id = routingContext.user().principal().getString(ClaimFieldName.USER_ID);
            String role = objectMapper.readValue(routingContext.user().principal().getString(ClaimFieldName.GROUPS), new TypeReference<List<String>>() {
            }).get(0);
            this.userDeviceService.RemoveUserDevice(user_id,role,token);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8");
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
