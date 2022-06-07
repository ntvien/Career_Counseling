package group12.career_counseling.web_service.ddd.user.counselor.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequest {
    private String email;
    private String phone;
    private String universityId;
}
