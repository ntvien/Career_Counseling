package group12.career_counseling.web_service.ddd.user.counselor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Embedded;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.mongodb.generic.PO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Counselor extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
    private String firebaseId;
    private String phone;
    private String email;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId universityId;
    private Counselor.Status lastStatus;

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
