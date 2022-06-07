package group12.career_counseling.web_service.ddd.user.counselor.repository;

import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CounselorRepository extends GenericRepository<Counselor>  implements ICounselorRepository {
    private MongoDBOperator<Counselor> mongoDBOperator;

    @Autowired
    public CounselorRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.USER, CollectionNameEnum.COUNSELORS, Counselor.class);
    }
    @Override
    public MongoDBOperator<Counselor> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
