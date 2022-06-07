package group12.career_counseling.web_service.ddd.community.comment.CommentRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.dozd.mongo.annotation.Embedded;
import eu.dozd.mongo.annotation.Id;
import group12.career_counseling.web_service.ddd.community.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private String  resourceId;
    private String userId;
    private String typeOfResource;
    private String contentComment;
}
