package group12.career_counseling.web_service.ddd.chat.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.chat.group.Group;
import group12.career_counseling.web_service.ddd.chat.group.service.IGroupService;
import group12.career_counseling.web_service.ddd.chat.message.service.IMessageService;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private ICounselorService counselorService;
    @Autowired
    private IUniversityService universityService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    public void getMessagesByGroupId(RoutingContext routingContext) {
        try {
            String user_id = routingContext.user().principal().getString(ClaimFieldName.USER_ID);
            String role = objectMapper.readValue(routingContext.user().principal().getString(ClaimFieldName.GROUPS), new TypeReference<List<String>>() {
            }).get(0);
            String groupId = routingContext.pathParam("groupid");
            Group group = this.groupService.getGroupByID(groupId);
            if (!group.getStudent().getId().toString().equals(user_id)) {
                Counselor counselor = null;
                if (role.equals(Roles.COUNSELOR))
                    counselor = counselorService.getCounselorById(user_id);
                if (counselor == null || !group.getUniversity().getId().toString().equals(counselor.getUniversityId().toString()))
                    throw new HttpException(StatusCodes.UNAUTHORIZED);
            }
            List<String> off = routingContext.queryParam("offset");
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(messageService.getMessageByGroupId(
                            groupId,
                            Integer.parseInt(routingContext.queryParam("offset").get(0)),
                            Integer.parseInt(routingContext.queryParam("limit").get(0)))
                    ));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
