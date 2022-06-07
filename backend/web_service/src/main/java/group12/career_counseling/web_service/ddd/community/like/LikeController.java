package group12.career_counseling.web_service.ddd.community.like;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.community.comment.CommentRequest.CommentRequest;
import group12.career_counseling.web_service.ddd.community.like.request.LikeRequest;
import group12.career_counseling.web_service.ddd.community.like.service.ILikeService;
import group12.career_counseling.web_service.ddd.community.response.CountResponse;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LikeController {
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ObjectMapper objectMapper;

    public void countLike(RoutingContext routingContext) {
        try {
            String resourceId = routingContext.queryParam("resourceId").isEmpty() ? "" : routingContext.queryParam("resourceId").get(0);
            long number = likeService.countLike(resourceId);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(  objectMapper.writeValueAsString(number));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void checkUserLike(RoutingContext routingContext) {
        try {
            String resourceId = routingContext.queryParam("resourceId").isEmpty() ? "" : routingContext.queryParam("resourceId").get(0);
            String student_id = routingContext.user().principal().getString("userId");
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(likeService.checkUserLike(student_id, resourceId)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void insert(RoutingContext routingContext) {
        try {
            LikeRequest likeRequest = JSON.parseObject(routingContext.getBodyAsString(), LikeRequest.class);

            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(likeService.insertLike(likeRequest)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
