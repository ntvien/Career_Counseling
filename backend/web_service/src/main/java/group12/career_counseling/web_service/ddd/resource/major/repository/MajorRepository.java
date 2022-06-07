package group12.career_counseling.web_service.ddd.resource.major.repository;

import group12.career_counseling.web_service.ddd.resource.major.Major;
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
public class MajorRepository extends GenericRepository<Major> implements IMajorRepository {
    private final MongoDBOperator<Major> mongoDBOperator;

    @Autowired
    public MajorRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.RESOURCE, CollectionNameEnum.MAJORS, Major.class);
    }

    @Override
    public MongoDBOperator<Major> getMongoDBOperator() {
        return mongoDBOperator;
    }

    @Override
    public void updateView(String id, int i) {
        this.mongoDBOperator.updateOne(new Document("_id", new ObjectId(id)),
                new Document()
                        .append(ArrayQueryOperators.INCREASE, new Document("viewNumber", i)));
    }
}
