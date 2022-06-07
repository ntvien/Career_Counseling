package group12.career_counseling.web_service.ddd.chat.message;

import eu.dozd.mongo.annotation.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reaction {
    private ObjectId user_id;
    private String react_id;
}
