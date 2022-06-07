package group12.career_counseling.web_service.ddd.chat.message.repository;

import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.chat.GroupNameField;
import group12.career_counseling.web_service.utils.enumeration.chat.MessageNameField;
import group12.career_counseling.web_service.utils.enumeration.mongodb.ArrayQueryOperators;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository extends GenericRepository<Message> implements IMessageRepository {
    private final MongoDBOperator<Message> mongoDBOperator;

    @Autowired
    public MessageRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.CHAT, CollectionNameEnum.MESSAGES, Message.class);
    }

    @Override
    public MongoDBOperator<Message> getMongoDBOperator() {
        return mongoDBOperator;
    }

    @Override
    public Boolean updateUserReadMessage(String messageId, Message.UserRead userRead) {
        return this.mongoDBOperator.updateOne(new Document(MessageNameField.ID, new ObjectId(messageId)),
                new Document(ArrayQueryOperators.PUSH,
                        new Document(MessageNameField.USER_READS, userRead)));
    }
}
