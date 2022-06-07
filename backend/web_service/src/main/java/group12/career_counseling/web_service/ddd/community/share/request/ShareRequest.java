package group12.career_counseling.web_service.ddd.community.share.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareRequest {
    private String  resourceId;
    private String userId;
}
