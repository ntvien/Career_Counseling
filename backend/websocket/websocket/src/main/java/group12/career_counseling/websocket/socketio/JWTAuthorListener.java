package group12.career_counseling.websocket.socketio;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import group12.career_counseling.web_service.utils.Security.JwtUtil;
import group12.career_counseling.web_service.utils.enumeration.authentication.AttributeUserJWT;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import group12.career_counseling.web_service.utils.enumeration.authentication.JsonFieldJWT;
import group12.career_counseling.web_service.vertx.rest.authentication.MyAuthProvider;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class JWTAuthorListener implements AuthorizationListener {
    private final JWTAuth jwtAuth;
    private final Logger logger = LoggerFactory.getLogger(JWTAuthorListener.class);

    @Autowired
    public JWTAuthorListener(MyAuthProvider jwtAuthProvider) {
        this.jwtAuth = jwtAuthProvider.getJwtAuth();
    }

    @Override
    public boolean isAuthorized(HandshakeData data) {
        AtomicReference<Boolean> result = new AtomicReference<>(true);
        try {
            this.jwtAuth.authenticate(new JsonObject().put(JsonFieldJWT.TOKEN,
                            JwtUtil.getTokenBearer(data.getHttpHeaders().get("Authorization"))))
                    .onSuccess(user -> {
                        data.getHttpHeaders().add(AttributeUserJWT.USER_ID, user.principal().getString(ClaimFieldName.USER_ID));
                        data.getHttpHeaders().add(AttributeUserJWT.TYPE_USER, user.principal().getJsonArray(ClaimFieldName.GROUPS));
                        result.set(true);
                    })
                    .onFailure(err -> {
                        result.set(true);
                    });
        } catch (Exception exception) {
            logger.info(exception.getMessage());
        }
        return result.get();
    }
}
