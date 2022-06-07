package group12.career_counseling.web_service.vertx.rest.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Component
public class APIExceptionHandler implements ErrorHandler {
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void handle(RoutingContext routingContext) {
        try {
            Throwable throwable = routingContext.failure();
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) routingContext.failure();
                ExceptionBody exception = ExceptionBody.builder()
                        .message(httpException.getPayload())
                        .timestamp(System.currentTimeMillis())
                        .build();
                routingContext.response()
                        .setStatusCode(httpException.getStatusCode())
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(objectMapper.writeValueAsString(exception));
            } else {
                routingContext.response()
                        .setStatusCode(routingContext.statusCode())
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end();
            }
            LOGGER.error(throwable);
        } catch (Exception exception) {
            routingContext.response()
                    .setStatusCode(StatusCodes.INTERNAL_SERVER_ERROR)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end();
            LOGGER.error(exception);
        }
    }
}
