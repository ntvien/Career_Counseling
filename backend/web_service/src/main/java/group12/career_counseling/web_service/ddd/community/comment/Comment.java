package group12.career_counseling.web_service.ddd.community.comment;

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
public class Comment extends PO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId resourceId;
    @JsonSerialize(using = ToStringSerializer.class)
    private String typeOfResource;
    private UserComment userComment;
    private long helpful;
    private long unhelpful;
    private List<UserEvaluate> userEvaluates;
    private String contentComment;
    @Data
    @Builder
    @Embedded
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserComment{
        @JsonSerialize(using = ToStringSerializer.class)
        private ObjectId userId;
        private String userName;
    }
    @Data
    @Builder
    @Embedded
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserEvaluate{
        @JsonSerialize(using = ToStringSerializer.class)
        private ObjectId userId;
        private String typeOfEvaluate;
    }
}
