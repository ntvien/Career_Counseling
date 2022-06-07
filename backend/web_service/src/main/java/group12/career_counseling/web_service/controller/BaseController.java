package group12.career_counseling.web_service.controller;

import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    private static final Logger LOGGER = LogManager.getLogger();

    public void index(RoutingContext routingContext) {
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end("ok");
        } catch (Throwable throwable) {
            LOGGER.error(throwable);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end("error");
        }
    }
}
