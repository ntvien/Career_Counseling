package group12.career_counseling.web_service.ddd.community.comment;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.community.comment.CommentRequest.CommentRequest;
import group12.career_counseling.web_service.ddd.community.comment.service.ICommentService;
import group12.career_counseling.web_service.ddd.community.response.CountResponse;
import group12.career_counseling.web_service.ddd.user.student.request.StudentRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ObjectMapper objectMapper;

    public void getComments(RoutingContext routingContext) {
        try {
            int offset = routingContext.queryParam("offset").isEmpty() ? 0 : Integer.parseInt(routingContext.queryParam("offset").get(0));
            int limit = routingContext.queryParam("limit").isEmpty() ? 0 : Integer.parseInt(routingContext.queryParam("limit").get(0));
            String resourceId = routingContext.queryParam("resourceId").isEmpty() ? "" : routingContext.queryParam("resourceId").get(0);
            boolean isCount = !routingContext.queryParam("isCount").isEmpty() && Boolean.parseBoolean(routingContext.queryParam("isCount").get(0));
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8");
            if (isCount)
                routingContext.response().end(objectMapper.writeValueAsString(CountResponse.builder().count(commentService.countComment(resourceId)).build()));
            else
                routingContext.response().end(objectMapper.writeValueAsString(commentService.getComment(resourceId, offset, limit)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void insert(RoutingContext routingContext) {
        try {
            CommentRequest commentRequest = JSON.parseObject(routingContext.getBodyAsString(), CommentRequest.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(commentService.insertComment(commentRequest)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void updateEvaluateComment(RoutingContext routingContext) {
        try {
            String student_id = routingContext.user().principal().getString("userId");
            JsonObject body = routingContext.getBodyAsJson();
            String commentId = body.getString("commentId");
            String typeOfEvaluate = body.getString("typeOfEvaluate");
            commentService.updateCommentEvaluate(commentId, student_id, typeOfEvaluate);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(String.valueOf(true));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
