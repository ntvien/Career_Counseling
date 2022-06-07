package group12.career_counseling.web_service.ddd.community.comment.repository;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;

public interface ICommentRepository extends IGenericRepository<Comment> {

    void updateUserEvaluateComment(String commentId, Comment.UserEvaluate userEvaluate);
}
