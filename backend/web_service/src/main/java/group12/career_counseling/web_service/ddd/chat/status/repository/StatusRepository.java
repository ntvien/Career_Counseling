package group12.career_counseling.web_service.ddd.chat.status.repository;

import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.ddd.chat.status.Status;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepository extends GenericRepository<Status> implements IStatusRepository {
    private final MongoDBOperator<Status> mongoDBOperator;

    @Autowired
    public StatusRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.CHAT, CollectionNameEnum.STATUS, Status.class);
    }

    @Override
    public MongoDBOperator<Status> getMongoDBOperator() {
        return mongoDBOperator;
    }

}
