package group12.career_counseling.web_service.ddd.chat.group.repository;

import group12.career_counseling.web_service.ddd.chat.group.Group;
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
public class GroupRepository extends GenericRepository<Group> implements IGroupRepository {
    private final MongoDBOperator<Group> mongoDBOperator;

    @Autowired
    public GroupRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.CHAT, CollectionNameEnum.GROUPS, Group.class);
    }

    @Override
    public MongoDBOperator<Group> getMongoDBOperator() {
        return mongoDBOperator;
    }

    @Override
    public Boolean updateUserReadLastMessage(String groupId, String userIdRead) {
        return this.mongoDBOperator.updateOne(new Document(GroupNameField.GROUP_ID,new ObjectId(groupId)),
                new Document(ArrayQueryOperators.PUSH,
                        new Document(GroupNameField.LAST_MESSAGE+'.'+"userIdReads",userIdRead)));
    }
}
