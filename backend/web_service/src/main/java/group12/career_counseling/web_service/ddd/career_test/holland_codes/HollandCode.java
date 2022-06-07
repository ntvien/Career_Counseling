package group12.career_counseling.web_service.ddd.career_test.holland_codes;

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
public class HollandCode extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    private String name;
    private String code;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
}
