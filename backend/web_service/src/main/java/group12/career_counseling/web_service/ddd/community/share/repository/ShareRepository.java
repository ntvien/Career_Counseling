package group12.career_counseling.web_service.ddd.community.share.repository;

import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.ddd.community.share.Share;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShareRepository extends GenericRepository<Share> implements IShareRepository {
    private final MongoDBOperator<Share> mongoDBOperator;

    @Autowired
    public ShareRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.COMMUNITY, CollectionNameEnum.SHARES, Share.class);
    }

    @Override
    public MongoDBOperator<Share> getMongoDBOperator() {
        return mongoDBOperator;
    }

}
