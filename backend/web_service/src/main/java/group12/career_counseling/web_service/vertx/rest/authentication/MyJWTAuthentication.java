package group12.career_counseling.web_service.vertx.rest.authentication;

import group12.career_counseling.web_service.utils.enumeration.authentication.AttributeUserJWT;
import group12.career_counseling.web_service.utils.enumeration.authentication.JsonFieldJWT;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MyJWTAuthentication implements IMyJWTAuthentication {

    private final static int TOKEN_EXPIRATION_TIME = 24 * 60;
    private final static int REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyJWTAuthentication.class);
    private final JWTAuth jwtAuth;

    @Autowired
    public MyJWTAuthentication(MyAuthProvider jwtAuthProvider) {
        this.jwtAuth = jwtAuthProvider.getJwtAuth();
    }

    private AuthenticationResponse generateToken(JsonObject claim) {
        var token = this.jwtAuth.generateToken(claim, new JWTOptions().setExpiresInMinutes(TOKEN_EXPIRATION_TIME));
        var refreshToken = this.jwtAuth.generateToken(claim, new JWTOptions().setExpiresInMinutes(REFRESH_TOKEN_EXPIRATION_TIME));
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse generateToken(JWTClaim claim) {
        return this.generateToken(JsonObject.mapFrom(claim));
    }

    @Override
    public Optional<AuthenticationResponse> refreshToken(String refreshToken) {
        AtomicReference<AuthenticationResponse> token = new AtomicReference<>();
        this.jwtAuth.authenticate(new JsonObject().put(JsonFieldJWT.TOKEN, refreshToken))
                .onSuccess(user -> {
                    token.set(this.generateToken(user.attributes().getJsonObject(AttributeUserJWT.ACCESS_TOKEN)));
                })
                .onFailure(err -> {
                    LOGGER.error("Refresh Token False,Detail: " + err.getMessage());
                    throw new RuntimeException(err.getMessage());
                });

        return Optional.ofNullable(token.get());
    }

    ;
}
