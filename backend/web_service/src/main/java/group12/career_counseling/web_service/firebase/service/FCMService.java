package group12.career_counseling.web_service.firebase.service;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import group12.career_counseling.web_service.firebase.model.PushNotificationRequest;
import group12.career_counseling.web_service.firebase.model.SubscriptionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {
    private Logger logger = LoggerFactory.getLogger(FCMService.class);
    public void sendMessageToToken(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        MulticastMessage message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        BatchResponse response = sendAndGetResponse(message);

    }

    public void subscribeToTopic(SubscriptionRequest subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(List.of(subscriptionRequestDto.getTopicName()),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            logger.error("Firebase subscribe to topic fail", e);
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequest subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(subscriptionRequestDto.getTokens()),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            logger.error("Firebase unsubscribe from topic fail", e);
        }
    }



    private BatchResponse sendAndGetResponse(MulticastMessage message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendMulticastAsync(message).get();
    }


    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setClickAction("reply")
                        .setColor("#F54B55")
                        .setIcon("@mipmap/logo")
                        .setDefaultSound(true)
                        .setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private MulticastMessage getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).addAllTokens(request.getToken())
                .build();
    }

    private MulticastMessage getPreconfiguredMessageToTokenWithData(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(data).addAllTokens(request.getToken())
                .build();
    }



    private MulticastMessage.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return MulticastMessage.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(
                        Notification.builder().setImage(request.getImageUri()).setTitle(request.getTitle()).setBody(request.getMessage()).build());
    }
}
