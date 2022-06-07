package group12.career_counseling.web_service.ddd.notification.user_device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.mongodb.generic.PO;
import lombok.*;
import org.bson.types.ObjectId;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserDevice extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private ObjectId _id;
    @Builder.Default
    private Boolean isDeleted = false;
    private String token;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private ObjectId UserId;
}
