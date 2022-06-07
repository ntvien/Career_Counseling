package group12.career_counseling.web_service.ddd.chat.status.services;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import group12.career_counseling.web_service.ddd.chat.message.repository.IMessageRepository;
import group12.career_counseling.web_service.ddd.chat.status.Status;
import group12.career_counseling.web_service.ddd.chat.status.repository.IStatusRepository;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.utils.QueryFactory;
import group12.career_counseling.web_service.utils.enumeration.chat.StatusNameField;
import group12.career_counseling.web_service.utils.enumeration.mongodb.QueryOperators;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService implements IStatusService {

    @Autowired
    private IStatusRepository statusRepository;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ICounselorService counselorService;

    @Override
    public List<Status> getStatus(String userIds, int page, int size) {
        return this.statusRepository.getMany(new Document(StatusNameField.USER_ID, userIds),
                        new Document(StatusNameField.TIME_AT, -1), page, size)
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Status insert(String userId, String role, String status, long timeAt) {
        String id = userId;
        if (role.equals(Roles.STUDENT))
            this.studentService.updateStatus(userId, status, timeAt);
        else {
            this.counselorService.updateStatus(userId, status, timeAt);
            id = this.counselorService.getCounselorById(userId).getUniversityId().toString();
        }

        return statusRepository.insert(Status
                .builder()
                .userId(new ObjectId(id))
                .status(status)
                .timeAt(timeAt)
                .build()).orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
    }
}
