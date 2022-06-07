package group12.career_counseling.web_service.ddd.resource.university;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Embedded;
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
public class University extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
    private String name;
    @JsonIgnore
    private String nameUnsigned;
    private String description;
    private String logo;
    private String image;
    private Contact contact;
    private Integer starNumber;
    private Integer viewNumber;
    @AllArgsConstructor
    @Embedded
    @Data
    @NoArgsConstructor
    public static class Contact {
        private String address;
        private String phone;
        private String email;
        private String website;
    }
}
