package group12.career_counseling.web_service.ddd.notification.notification.repository;

import group12.career_counseling.web_service.ddd.notification.notification.Notification;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository extends GenericRepository<Notification> implements INotificationRepository {
    private final MongoDBOperator<Notification> mongoDBOperator;

    @Autowired
    public NotificationRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.NOTIFICATION, CollectionNameEnum.NOTIFICATIONS, Notification.class);
    }

    @Override
    public MongoDBOperator<Notification> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
