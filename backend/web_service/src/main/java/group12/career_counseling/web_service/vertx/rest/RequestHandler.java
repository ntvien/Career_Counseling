package group12.career_counseling.web_service.vertx.rest;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RequestHandler {
    HttpMethod getMethod();

    String getPath();

    Handler<RoutingContext> handle();

    Boolean isEnableAuthentication();

    List<String> getAuthRoles();

    static RequestHandler init(HttpMethod httpMethod, String path, Handler<RoutingContext> handler, Boolean isEnableAuthentication, String... roles) {
        return new RequestHandler() {
            @Override
            public HttpMethod getMethod() {
                return httpMethod;
            }

            @Override
            public String getPath() {
                return path;
            }

            @Override
            public Handler<RoutingContext> handle() {
                return handler;
            }

            @Override
            public Boolean isEnableAuthentication() {
                return isEnableAuthentication;
            }

            @Override
            public List<String> getAuthRoles() {
                if (roles != null) {
                    return List.of(roles);
                }
                return new ArrayList<>();
            }
        };
    }
}
