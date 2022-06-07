package group12.career_counseling.web_service.ddd.community.comment.service;

import group12.career_counseling.web_service.ddd.community.comment.Comment;
import group12.career_counseling.web_service.ddd.community.comment.CommentRequest.CommentRequest;
import group12.career_counseling.web_service.ddd.community.comment.repository.ICommentRepository;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IStudentService studentService;

    @Override
    public Comment insertComment( CommentRequest commentRequest) {
        Student student = this.studentService.getStudentById(commentRequest.getUserId());
        return this.commentRepository.insert(Comment.builder()
                .contentComment(commentRequest.getContentComment())
                .resourceId(new ObjectId(commentRequest.getResourceId()))
                .typeOfResource(commentRequest.getTypeOfResource())
                .userEvaluates(Collections.emptyList())
                .userComment(Comment.UserComment.builder()
                        .userId(student.get_id())
                        .userName(student.getFullName())
                        .build()).build()).orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
    }

    @Override
    public List<Comment> getComment(String resourceId, int offset, int limit) {
        return this.commentRepository.getMany(new Document("resourceId", new ObjectId(resourceId)),
                new Document("helpful", 1), offset, limit).orElse(Collections.emptyList());
    }

    @Override
    public Long countComment(String ResourceId) {
        return this.commentRepository.count(new Document("resourceId", new ObjectId(ResourceId))).orElse(0L);
    }

    @Override
    public void updateCommentEvaluate(String commentId, String userId, String typeOfEvaluate) {
        Comment comment = this.commentRepository.getById(commentId)
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
        if (comment.getUserEvaluates()==null || comment.getUserEvaluates()
                .stream()
                .noneMatch(userEvaluate -> userEvaluate
                        .getUserId()
                        .toString()
                        .equals(userId))) {
            this.commentRepository.updateUserEvaluateComment(commentId, Comment.UserEvaluate.builder()
                    .typeOfEvaluate(typeOfEvaluate)
                    .userId(new ObjectId(userId))
                    .build());
        }
    }
}
