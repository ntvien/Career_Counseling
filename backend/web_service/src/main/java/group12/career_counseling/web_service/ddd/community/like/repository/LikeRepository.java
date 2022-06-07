package group12.career_counseling.web_service.ddd.community.like.repository;

import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository extends GenericRepository<Like> implements ILikeRepository {
    private final MongoDBOperator<Like> mongoDBOperator;

    @Autowired
    public LikeRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.COMMUNITY, CollectionNameEnum.LIKES, Like.class);
    }

    @Override
    public MongoDBOperator<Like> getMongoDBOperator() {
        return mongoDBOperator;
    }

}
