package group12.career_counseling.web_service.vertx;

import io.vertx.core.Vertx;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class VertxProvider {
    private static Vertx vertx;

    public static synchronized Vertx getInstance() {
        if(vertx == null){
            vertx = Vertx.vertx();
        }
        return vertx;
    }
}
