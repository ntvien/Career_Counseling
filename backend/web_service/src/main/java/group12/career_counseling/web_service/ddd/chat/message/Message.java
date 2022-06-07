package group12.career_counseling.web_service.ddd.chat.message;

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
public class Message extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;

    private String contentMessage;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId  userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId groupId;
    private Boolean isCounselor;
    private List<UserRead> userReads;
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @Embedded
    public static class UserRead{
        @JsonSerialize(using = ToStringSerializer.class)
        private ObjectId userId;
        private long createdTime;
    }
}