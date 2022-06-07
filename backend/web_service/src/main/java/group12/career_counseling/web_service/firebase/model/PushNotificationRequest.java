package group12.career_counseling.web_service.firebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushNotificationRequest {
    private String title;
    private String message;
    @Builder.Default
    private String imageUri ="https://firebasestorage.googleapis.com/v0/b/career-couseling.appspot.com/o/user.PNG?alt=media&token=91c8010a-81dd-4370-aca5-940525c8f64d";
    private String topic;
    private List<String> token;
}
