package group12.career_counseling.web_service.ddd.chat.message.request;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {

    private String contentMessage;
    private String   userId;
    private String  groupId;
    private Boolean isCounselor;
    private long createdTime;
}
