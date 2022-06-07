package group12.career_counseling.web_service.ddd.resource.feature.repository;

import group12.career_counseling.web_service.ddd.resource.feature.Feature;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FeatureRepository extends GenericRepository<Feature> implements IFeatureRepository {
    private final MongoDBOperator<Feature> mongoDBOperator;

    @Autowired
    public FeatureRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.RESOURCE, CollectionNameEnum.FEATURES, Feature.class);
    }

    @Override
    public MongoDBOperator<Feature> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
