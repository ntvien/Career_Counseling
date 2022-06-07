package group12.career_counseling.web_service.vertx.rest.authentication;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthenticationHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

import java.util.Optional;

public interface IMyJWTAuthentication {

    AuthenticationResponse generateToken(JWTClaim claim);

    Optional<AuthenticationResponse> refreshToken(String refreshToken);
}
