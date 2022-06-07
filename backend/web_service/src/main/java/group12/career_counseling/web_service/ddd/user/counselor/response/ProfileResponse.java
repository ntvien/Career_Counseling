package group12.career_counseling.web_service.ddd.user.counselor.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import lombok.*;
import org.bson.types.ObjectId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    private String phone;
    private String email;
    private Counselor.Status lastStatus;
    private University university;
}
