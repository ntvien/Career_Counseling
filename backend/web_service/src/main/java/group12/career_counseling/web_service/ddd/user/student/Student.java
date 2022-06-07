package group12.career_counseling.web_service.ddd.user.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Embedded;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.ddd.chat.status.Status;
import group12.career_counseling.web_service.mongodb.generic.PO;
import group12.career_counseling.web_service.utils.enumeration.user.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.bind.DefaultValue;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Student extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @Builder.Default
    @JsonIgnore
    private Boolean isDeleted = false;
    private String userName;
    @JsonIgnore
    private String password;
    private String fullName;
    @JsonIgnore
    private String fullNameUnsigned;
    private Status lastStatus;
    private String avatar;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @Embedded
    public static class Status {
        private String status;
        private long timeAt;
    }
}
