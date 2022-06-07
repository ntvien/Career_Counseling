package group12.career_counseling.web_service.ddd.notification.notification.service;

import group12.career_counseling.web_service.ddd.notification.notification.Notification;
import group12.career_counseling.web_service.ddd.notification.notification.repository.INotificationRepository;
import group12.career_counseling.web_service.ddd.notification.user_device.UserDevice;
import group12.career_counseling.web_service.ddd.notification.user_device.service.IUserDeviceService;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.firebase.model.PushNotificationRequest;
import group12.career_counseling.web_service.firebase.service.PushNotificationService;
import group12.career_counseling.web_service.utils.enumeration.chat.MessageNameField;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private INotificationRepository notificationRepository;
    @Autowired
    private PushNotificationService pushNotificationService;
    @Autowired
    private IUserDeviceService userDeviceService;
    @Autowired
    private ICounselorService counselorService;

    @Override
    public List<Notification> getNotifications(String userId, String role, int offset, int limit) {
        String id = userId;
        if (role.equals(Roles.COUNSELOR)) {
            Counselor counselor = this.counselorService.getCounselorById(userId);
            id = counselor.getUniversityId().toString();
        }
        return this.notificationRepository.getMany(new Document()
                        .append("userId", new ObjectId(id)),
                new Document()
                        .append(MessageNameField.CREATE_TIME, -1),
                offset,
                limit).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Notification pushNotification(Notification notification) {
        List<UserDevice> userDevices = this.userDeviceService
                .getUserDevices(notification.getUserId().toString());
        List<String> tokens = userDevices.stream().map(UserDevice::getToken).collect(Collectors.toList());
        this.pushNotificationService.sendPushNotificationToTokens(PushNotificationRequest.builder()
                .message(notification.getMessage())
                .title(notification.getTitle())
                .imageUri(notification.getImage())
                .token(tokens)
                .build());
        return this.notificationRepository.insert(notification)
                .orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
    }

    @Override
    public long countNotificationUnread(String userId, String role) {
        String id = userId;
        if (role.equals(Roles.COUNSELOR)) {
            Counselor counselor = this.counselorService.getCounselorById(userId);
            id = counselor.getUniversityId().toString();
        }
        return this.notificationRepository.count(new Document()
                .append("userId", new ObjectId(id))
                .append("isRead", false)).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public void setReadNotification(String userId, String role) {
        String id = userId;
        if (role.equals(Roles.COUNSELOR)) {
            Counselor counselor = this.counselorService.getCounselorById(userId);
            id = counselor.getUniversityId().toString();
        }
        this.notificationRepository.update(id, Notification.builder().isRead(true).build())
                .orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
    }
}
