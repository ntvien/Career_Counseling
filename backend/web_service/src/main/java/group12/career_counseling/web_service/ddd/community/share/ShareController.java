package group12.career_counseling.web_service.ddd.community.share;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.community.like.request.LikeRequest;
import group12.career_counseling.web_service.ddd.community.share.request.ShareRequest;
import group12.career_counseling.web_service.ddd.community.share.service.IShareService;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ShareController {
    @Autowired
    private IShareService service;
    @Autowired
    private ObjectMapper objectMapper;

    public void countLike(RoutingContext routingContext) {
        try {
            String resourceId = routingContext.queryParam("resourceId").isEmpty() ? "" : routingContext.queryParam("resourceId").get(0);
            long number = service.countShare(resourceId);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(number));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void insert(RoutingContext routingContext) {
        try {
            ShareRequest request = JSON.parseObject(routingContext.getBodyAsString(), ShareRequest.class);

            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(service.insertShare(request)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
