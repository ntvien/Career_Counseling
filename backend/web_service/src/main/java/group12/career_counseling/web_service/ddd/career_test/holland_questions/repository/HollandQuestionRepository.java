package group12.career_counseling.web_service.ddd.career_test.holland_questions.repository;

import group12.career_counseling.web_service.ddd.career_test.holland_codes.HollandCode;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.repository.IHollandCodeRepository;
import group12.career_counseling.web_service.ddd.career_test.holland_questions.HollandQuestion;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.ArrayQueryOperators;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HollandQuestionRepository extends GenericRepository<HollandQuestion> implements IHollandQuestionRepository {
    private final MongoDBOperator<HollandQuestion> mongoDBOperator;

    @Autowired
    public HollandQuestionRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.CAREER_TEST, CollectionNameEnum.HOLLAND_QUESTIONS, HollandQuestion.class);
    }

    @Override
    public MongoDBOperator<HollandQuestion> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
