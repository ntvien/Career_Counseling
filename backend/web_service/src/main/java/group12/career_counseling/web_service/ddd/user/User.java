package group12.career_counseling.web_service.ddd.user;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    private String userName;
    private String fullName;
    private ObjectId universityId;
    private String role;
    private String phone;
    private String email;
}
