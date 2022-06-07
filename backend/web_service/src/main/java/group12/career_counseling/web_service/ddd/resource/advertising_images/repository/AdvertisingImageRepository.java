package group12.career_counseling.web_service.ddd.resource.advertising_images.repository;

import group12.career_counseling.web_service.ddd.resource.advertising_images.AdvertisingImage;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdvertisingImageRepository extends GenericRepository<AdvertisingImage> implements IAdvertisingImageRepository {
    private final MongoDBOperator<AdvertisingImage> mongoDBOperator;

    @Autowired
    public AdvertisingImageRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.RESOURCE, CollectionNameEnum.ADVERTISING_IMAGES, AdvertisingImage.class);
    }

    @Override
    public MongoDBOperator<AdvertisingImage> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
