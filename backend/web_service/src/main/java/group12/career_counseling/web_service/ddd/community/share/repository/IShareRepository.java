package group12.career_counseling.web_service.ddd.community.share.repository;

import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.ddd.community.share.Share;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShareRepository extends IGenericRepository<Share> {


}
