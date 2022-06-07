package group12.career_counseling.web_service.ddd.community.like.service;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.ddd.community.comment.CommentRequest.CommentRequest;
import group12.career_counseling.web_service.ddd.community.like.Like;
import group12.career_counseling.web_service.ddd.community.like.request.LikeRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ILikeService {
    Like insertLike(LikeRequest likeRequest);
    Long countLike(String ResourceId);
    Boolean checkUserLike(String userId, String resourceId);
}
