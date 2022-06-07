package group12.career_counseling.web_service.ddd.community.comment.service;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.ddd.community.comment.CommentRequest.CommentRequest;

import java.util.List;

public interface ICommentService {

    Comment insertComment( CommentRequest commentRequest);
    List<Comment> getComment(String ResourceId, int offset, int limit);
    Long countComment(String ResourceId);
   void updateCommentEvaluate(String commentId, String userId,String  typeOfEvaluate);

}
