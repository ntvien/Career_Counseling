package group12.career_counseling.web_service.vertx.rest;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface ResponseTimeHandler extends Handler<RoutingContext> {

    /**
     * Create a handler
     *
     * @return the handler
     */
    static ResponseTimeHandler create() {
        return new ResponseTimeHandlerImpl();
    }

}