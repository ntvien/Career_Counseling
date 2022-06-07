package group12.career_counseling.web_service.ddd.notification.user_device.service;

import group12.career_counseling.web_service.ddd.notification.user_device.UserDevice;
import group12.career_counseling.web_service.ddd.notification.user_device.repository.IUserDeviceRepository;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDeviceService implements IUserDeviceService {
    @Autowired
    private IUserDeviceRepository userDeviceRepository;
    @Autowired
    private ICounselorService counselorService;

    @Override
    public void AddUserDevice(String userId, String role, String token) {
        String id = userId;
        if (role.equals(Roles.COUNSELOR)) {
            Counselor counselor = this.counselorService.getCounselorById(userId);
            id = counselor.getUniversityId().toString();
        }
        String finalId = id;
        this.userDeviceRepository.getByQuery(new Document()
                .append("userId", new ObjectId(id))
                .append("token", token)).or(() -> {
             return this.userDeviceRepository.insert(UserDevice.builder()
                    .token(token)
                    .UserId(new ObjectId(finalId))
                    .build());
        });
    }

    @Override
    public void RemoveUserDevice(String userId, String role, String token) {
        String id = userId;
        if (role.equals(Roles.COUNSELOR)) {
            Counselor counselor = this.counselorService.getCounselorById(userId);
            id = counselor.getUniversityId().toString();
        }
        this.userDeviceRepository.deleteMany(new Document()
                .append("userId", id)
                .append("token", token));
    }

    @Override
    public List<UserDevice> getUserDevices(String userId) {
        return this.userDeviceRepository.getAll(new Document("userId", new ObjectId( userId)), new Document())
                .orElse(Collections.emptyList());
    }
}
