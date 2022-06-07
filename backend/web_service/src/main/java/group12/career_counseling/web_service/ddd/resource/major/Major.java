package group12.career_counseling.web_service.ddd.resource.major;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.mongodb.generic.PO;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Major extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private ObjectId _id;
    private String name;
    @JsonIgnore
    private String nameUnsigned;
    private String description;
    private String image;
    private List<ObjectId> universities;
    private Integer starNumber;
    private Integer viewNumber;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
}
