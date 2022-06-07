package group12.career_counseling.web_service.ddd.chat.message.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReadMessage {
    private String messageId;
    private String userId;
    private String groupId;
    private long createdTime;
}
