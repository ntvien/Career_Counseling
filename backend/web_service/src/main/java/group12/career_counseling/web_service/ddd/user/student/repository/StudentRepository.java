package group12.career_counseling.web_service.ddd.user.student.repository;

import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository extends GenericRepository<Student> implements IStudentRepository {
    private MongoDBOperator mongoDBOperator;

    @Autowired
    public StudentRepository(MongoDBClient mongoDBClient) {
        mongoDBOperator = new MongoDBOperator<>(mongoDBClient, DBNameEnum.USER, CollectionNameEnum.STUDENTS, Student.class);
    }

    @Override
    public MongoDBOperator<Student> getMongoDBOperator() {
        return mongoDBOperator;
    }
}
