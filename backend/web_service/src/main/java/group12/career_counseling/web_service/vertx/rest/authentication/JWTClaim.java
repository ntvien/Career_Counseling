package group12.career_counseling.web_service.vertx.rest.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTClaim {
    private String userId;
    private List<String> groups;
}
