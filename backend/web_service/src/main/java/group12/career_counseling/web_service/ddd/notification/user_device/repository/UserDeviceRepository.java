package group12.career_counseling.web_service.ddd.notification.user_device.repository;

import group12.career_counseling.web_service.ddd.notification.user_device.UserDevice;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDeviceRepository extends GenericRepository<UserDevice> implements IUserDeviceRepository {
    private final MongoDBOperator<UserDevice> mongoDBOperator;

    @Autowired
    public UserDeviceRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.NOTIFICATION, CollectionNameEnum.USER_DEVICES, UserDevice.class);
    }

    @Override
    public MongoDBOperator<UserDevice> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
