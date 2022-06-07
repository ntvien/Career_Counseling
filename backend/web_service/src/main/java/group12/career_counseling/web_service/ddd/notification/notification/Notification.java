package group12.career_counseling.web_service.ddd.notification.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.mongodb.generic.PO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.bind.DefaultValue;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Notification extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @Builder.Default
    private Boolean isDeleted = false;
    private String message;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId targetId;
    private String typeOfNotification;
    @Builder.Default
    private Boolean isRead =false;
    private String image;
}
