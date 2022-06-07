package group12.career_counseling.web_service.ddd.user.student.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    private String userId;
    private String oldPassword;
    private String newPassword;
}
