package group12.career_counseling.web_service.vertx.rest;

import group12.career_counseling.web_service.utils.enumeration.ApplicationProperties;
import group12.career_counseling.web_service.vertx.rest.authentication.MyAuthProvider;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.APIExceptionHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.auth.authorization.Authorization;
import io.vertx.ext.auth.authorization.OrAuthorization;
import io.vertx.ext.auth.authorization.PermissionBasedAuthorization;
import io.vertx.ext.auth.authorization.RoleBasedAuthorization;
import io.vertx.ext.auth.jwt.authorization.JWTAuthorization;
import io.vertx.ext.auth.jwt.authorization.MicroProfileAuthorization;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RESTfulVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger();
    private List<RequestHandler> requestHandlers = new ArrayList<>();
    @Autowired
    protected Environment environment;
    @Autowired
    private APIExceptionHandler APIExceptionHandler;
    private final MicroProfileAuthorization microProfileAuthorization;
    private final JWTAuthHandler jwtAuthHandler;

    @Autowired
    public RESTfulVerticle(MyAuthProvider authProvider) {
        this.microProfileAuthorization = MicroProfileAuthorization.create();
        this.jwtAuthHandler = JWTAuthHandler.create(authProvider.getJwtAuth());
    }

    private void configureRoutes(Router router) {

        this.requestHandlers.forEach(requestHandler -> {
            Route route = router.route(requestHandler.getMethod(), requestHandler.getPath());
            if (requestHandler.isEnableAuthentication()) {
                route.handler(jwtAuthHandler);
                OrAuthorization authorization = OrAuthorization.create();
                for (String role : requestHandler.getAuthRoles()) {
                    authorization.addAuthorization(RoleBasedAuthorization.create(role));
                }
                if (requestHandler.getAuthRoles().size() != 0) {
                    route.handler(AuthorizationHandler.create(authorization)
                            .addAuthorizationProvider(microProfileAuthorization));
                }
            }
            route.handler(requestHandler.handle())
                    .failureHandler(APIExceptionHandler);
            LOGGER.info("Configuring route {}:{}", requestHandler.getMethod(), requestHandler.getPath());
        });
    }

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create()
                .setDeleteUploadedFilesOnEnd(true)
                .setBodyLimit(5 * 1024 * 1024)); // 5 MB
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PATCH)
                .allowedHeader("Access-Control-Allow-Methods")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type")
                .allowedHeader("Authorization")
                .allowedHeader("Cache-Control")
                .allowedHeader("X-Requested-With")
                .allowedHeader("Accept")
                .allowedHeader("Origin"));

        router.route().handler(ResponseTimeHandler.create());

        configureRoutes(router);
        vertx.createHttpServer().requestHandler(router).listen(Integer.parseInt(environment.getProperty(ApplicationProperties.PORT, "8080")));
    }

    public void setRequestHandlers(List<RequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }
}
