package group12.career_counseling.web_service.ddd.community.comment.repository;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.chat.GroupNameField;
import group12.career_counseling.web_service.utils.enumeration.mongodb.ArrayQueryOperators;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends GenericRepository<Comment> implements ICommentRepository {
    private final MongoDBOperator<Comment> mongoDBOperator;

    @Autowired
    public CommentRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.COMMUNITY, CollectionNameEnum.COMMENTS, Comment.class);
    }

    @Override
    public MongoDBOperator<Comment> getMongoDBOperator() {
        return mongoDBOperator;
    }


    @Override
    public void updateUserEvaluateComment(String commentId, Comment.UserEvaluate userEvaluate) {
         this.mongoDBOperator.updateOne(new Document("_id",  new ObjectId(commentId)),
                new Document()
                .append(ArrayQueryOperators.PUSH,
                        new Document("userEvaluates",userEvaluate))
                .append(ArrayQueryOperators.INCREASE,new Document(userEvaluate.getTypeOfEvaluate(),1)));
    }
}
