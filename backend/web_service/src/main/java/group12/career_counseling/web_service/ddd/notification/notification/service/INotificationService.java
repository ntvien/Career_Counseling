package group12.career_counseling.web_service.ddd.notification.notification.service;

import group12.career_counseling.web_service.ddd.notification.notification.Notification;

import java.util.List;

public interface INotificationService {

    List<Notification> getNotifications(String userId,String role, int  offset, int limit);
    Notification pushNotification(Notification notification);
    long countNotificationUnread(String userId,String role);
    void setReadNotification(String id,String role);
}
