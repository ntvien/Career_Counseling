package group12.career_counseling.web_service.ddd.chat.group;
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
public class MemberOfGroup {
     @JsonSerialize(using = ToStringSerializer.class)
     private ObjectId id;
     private String full_name_unsigned;
     private String full_name;
     private String uri_avatar;
}
