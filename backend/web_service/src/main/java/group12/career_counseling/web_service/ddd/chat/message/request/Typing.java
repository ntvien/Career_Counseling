package group12.career_counseling.web_service.ddd.chat.message.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Typing {
    private String  userId;
    private String  groupId;
    private Boolean isCounselor;
    private  String action;
}
