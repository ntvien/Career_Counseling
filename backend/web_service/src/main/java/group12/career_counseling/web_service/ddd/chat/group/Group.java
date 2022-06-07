package group12.career_counseling.web_service.ddd.chat.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Embedded;
import eu.dozd.mongo.annotation.Entity;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.ddd.chat.message.Message;
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
public class Group extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
    private MemberOfGroup student;
    private MemberOfGroup university;
    private LastMessage lastMessage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embedded
    public static class MemberOfGroup {
        @JsonSerialize(using = ToStringSerializer.class)
        private ObjectId id;
        private String fullNameUnsigned;
        private String fullName;
        private String uriAvatar;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embedded
    public static class LastMessage {
        private String contentMessage;
        private String userId;
        private Boolean isCounselor;
        private List<String> userIdReads;
        private long createdTime;
    }
}
