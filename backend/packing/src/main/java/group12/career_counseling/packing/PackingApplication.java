package group12.career_counseling.packing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"group12.career_counseling.web_service","group12.career_counseling.websocket"})
public class 	PackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackingApplication.class, args);
	}

}
