package group12.career_counseling.web_service.ddd.community.like.repository;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import group12.career_counseling.web_service.mongodb.generic.GenericRepository;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;
import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import group12.career_counseling.web_service.utils.enumeration.mongodb.CollectionNameEnum;
import group12.career_counseling.web_service.utils.enumeration.mongodb.DBNameEnum;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends IGenericRepository<Like> {


}
