package group12.career_counseling.web_service.ddd.community.share;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.mongodb.generic.PO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Share extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId resourceId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
}
