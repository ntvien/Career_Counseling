package group12.career_counseling.web_service.ddd.community.share.service;

import group12.career_counseling.web_service.ddd.community.share.Share;
import group12.career_counseling.web_service.ddd.community.share.repository.IShareRepository;
import group12.career_counseling.web_service.ddd.community.share.request.ShareRequest;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareService implements IShareService {
    @Autowired
    private IShareRepository shareRepository;
    @Autowired
    private IStudentService studentService;

    @Override
    public Share insertShare(ShareRequest request) {
            return this.shareRepository.insert(Share.builder()
                    .resourceId(new ObjectId(request.getResourceId()))
                    .userId(new ObjectId(request.getUserId()))
                    .build())
                    .orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
    }

    @Override
    public Long countShare(String ResourceId) {
        return this.shareRepository.count(new Document("resourceId", new ObjectId(ResourceId))).orElse(0L);
    }


}
