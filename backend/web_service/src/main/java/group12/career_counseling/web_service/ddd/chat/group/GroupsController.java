package group12.career_counseling.web_service.ddd.chat.group;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.chat.group.service.IGroupService;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupsController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IGroupService groupService;

    public void getGroups(RoutingContext routingContext) {
        try {
            String userIdToken = routingContext.user().principal().getString(ClaimFieldName.USER_ID);
            String role = objectMapper.readValue(routingContext.user().principal().getString(ClaimFieldName.GROUPS), new TypeReference<List<String>>() {
            }).get(0);
            List<String> universityParam = routingContext.queryParam("universityid");
            String userId = routingContext.queryParam("userid").get(0);

            if (universityParam.isEmpty()) {
                int  offset =routingContext.queryParam("offset").isEmpty()? 0 :Integer.parseInt(routingContext.queryParam("offset").get(0));
                int  limit =routingContext.queryParam("limit").isEmpty()? 0 :Integer.parseInt(routingContext.queryParam("limit").get(0));

                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json; charset=utf-8")
                        .end(objectMapper.writeValueAsString(groupService.getGroups(userIdToken, role,
                                offset,
                                limit)
                        ));
            } else {
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json; charset=utf-8")
                        .end(objectMapper.writeValueAsString(groupService.CreateGroup(
                                universityParam.get(0),
                                userId
                        )));
            }
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
