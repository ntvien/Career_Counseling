package group12.career_counseling.web_service.ddd.community.like.service;

import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.ddd.community.like.repository.ILikeRepository;
import group12.career_counseling.web_service.ddd.community.like.request.LikeRequest;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService implements ILikeService {
    @Autowired
    private ILikeRepository likeRepository;
    @Autowired
    private IStudentService studentService;

    @Override
    public Like insertLike(LikeRequest likeRequest) {
        Student student = this.studentService.getStudentById(likeRequest.getUserId());
        if (!checkUserLike(likeRequest.getUserId(), likeRequest.getResourceId())) {
            return this.likeRepository.insert(Like.builder()
                    .resourceId(new ObjectId(likeRequest.getResourceId()))
                    .userId(student.get_id())
                    .build())
                    .orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
        } else return null;
    }

    @Override
    public Long countLike(String resourceId) {
        return this.likeRepository.count(new Document("resourceId", new ObjectId(resourceId))).orElse(0L);
    }

    @Override
    public Boolean checkUserLike(String userId, String resourceId) {
        Like like  =  this.likeRepository.getByQuery(new Document().append("userId",new ObjectId(userId)).append("resourceId",new ObjectId(resourceId))).orElse(null);
        return like !=null;
    }
}
