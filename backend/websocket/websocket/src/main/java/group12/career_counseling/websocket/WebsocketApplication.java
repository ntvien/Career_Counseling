package group12.career_counseling.websocket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import group12.career_counseling.websocket.socketio.JWTAuthorListener;
import group12.career_counseling.websocket.socketio.MyJsonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = {"group12.career_counseling.web_service", "group12.career_counseling.websocket"})
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

    @Autowired
    private Environment environment;
    @Value("${socket-server.port}")
    private Integer portSocket;
    @Value("${socket-server.host}")
    private String host;
    @Autowired
    private JWTAuthorListener jwtAuthorListener;
    @Autowired
    private MyJsonSupport myJsonSupport;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration configuration = new Configuration();
        configuration.setHostname(host);
        Integer herokuPort = environment.getProperty("PORT", Integer.class);
        configuration.setPort(herokuPort != null ? herokuPort : portSocket);
        configuration.setAuthorizationListener(jwtAuthorListener);
        configuration.setJsonSupport(myJsonSupport);
        return new SocketIOServer(configuration);
    }

}
