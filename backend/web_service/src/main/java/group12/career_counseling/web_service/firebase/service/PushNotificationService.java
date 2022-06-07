package group12.career_counseling.web_service.firebase.service;

import group12.career_counseling.web_service.firebase.model.PushNotificationRequest;
import group12.career_counseling.web_service.firebase.model.SubscriptionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotificationToTokens(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void subscribeToTopic(SubscriptionRequest request) {
        try {
            fcmService.subscribeToTopic(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequest request) {
        try {
            fcmService.unsubscribeFromTopic(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
