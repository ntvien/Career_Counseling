package group12.career_counseling.web_service.vertx.rest.authentication;


import group12.career_counseling.web_service.vertx.VertxProvider;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyAuthProvider {
    private final JWTAuth jwtAuth;

    @Autowired
    public MyAuthProvider(Environment environment) {
        JWTAuthOptions config = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("HS256")
                        .setBuffer(environment.getProperty("SECRET_KEY")));
        this.jwtAuth = JWTAuth.create(VertxProvider.getInstance(), config);
    }

    public JWTAuth getJwtAuth() {
        return jwtAuth;
    }
}
