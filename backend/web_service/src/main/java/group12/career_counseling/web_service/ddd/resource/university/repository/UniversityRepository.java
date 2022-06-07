package group12.career_counseling.web_service.ddd.resource.university.repository;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.ddd.resource.university.University;
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
public class UniversityRepository extends GenericRepository<University> implements IUniversityRepository {
    private final MongoDBOperator<University> mongoDBOperator;

    @Autowired
    public UniversityRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.RESOURCE, CollectionNameEnum.UNIVERSITIES, University.class);
    }

    @Override
    public MongoDBOperator<University> getMongoDBOperator() {
        return mongoDBOperator;
    }

    @Override
    public void updateView(String universityId, long amount) {
        this.mongoDBOperator.updateOne(new Document("_id", new ObjectId(universityId)),
                new Document()
                        .append(ArrayQueryOperators.INCREASE, new Document("viewNumber", amount)));
    }
}
