package group12.career_counseling.websocket.modules.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.namespace.Namespace;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.chat.group.Group;
import group12.career_counseling.web_service.ddd.chat.group.service.IGroupService;
import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.ddd.chat.message.request.MessageRequest;
import group12.career_counseling.web_service.ddd.chat.message.request.ReadMessage;
import group12.career_counseling.web_service.ddd.chat.message.request.Typing;
import group12.career_counseling.web_service.ddd.chat.message.service.IMessageService;
import group12.career_counseling.web_service.ddd.chat.status.Status;
import group12.career_counseling.web_service.ddd.chat.status.services.IStatusService;
import group12.career_counseling.web_service.ddd.notification.notification.Notification;
import group12.career_counseling.web_service.ddd.notification.notification.service.INotificationService;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.utils.enumeration.authentication.AttributeUserJWT;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import group12.career_counseling.web_service.utils.enumeration.chat.StatusEnum;
import group12.career_counseling.web_service.utils.enumeration.user.TypeUser;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import group12.career_counseling.websocket.enumeration.ChatEventName;
import group12.career_counseling.websocket.socketio.JWTAuthorListener;
import io.vertx.ext.web.handler.HttpException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static group12.career_counseling.websocket.enumeration.ChatEventName.*;


@Component
public class ChatModule {
    private static final Logger logger = LoggerFactory.getLogger(ChatModule.class);
    private final SocketIOServer server;
    private final Namespace namespace;

    private final IGroupService groupService;
    private final IMessageService messageService;
    private final IStudentService studentService;
    private final ICounselorService counselorService;
    private final IStatusService statusService;
    private final INotificationService notificationService;
    private JWTAuthorListener jwtAuthorListener;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ChatModule(SocketIOServer server,
                      IGroupService groupService,
                      IMessageService messageService,
                      IStudentService studentService,
                      ICounselorService counselorService,
                      IStatusService statusService,
                      INotificationService notificationService,
                      JWTAuthorListener jwtAuthorListener) {
        this.server = server;
        this.groupService = groupService;
        this.messageService = messageService;
        this.studentService = studentService;
        this.counselorService = counselorService;
        this.statusService = statusService;
        this.notificationService = notificationService;
        this.jwtAuthorListener = jwtAuthorListener;
        this.namespace = (Namespace) this.server.addNamespace(NameOfNameSpace);
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener(MessageNameEvent, MessageRequest.class, onMessageReceived());
        this.namespace.addEventListener(TypingEvent, Typing.class, onTypingReceived());
        this.namespace.addEventListener(READ_MESSAGE, ReadMessage.class, onReadMessage());
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
                logger.info("Client[{}] - ConnectEd to chat module through '{}'", client.getSessionId().toString(), handshakeData.getHttpHeaders());
                String user_id = handshakeData.getHttpHeaders().get(AttributeUserJWT.USER_ID);

                String typeUser = handshakeData.getHttpHeaders().get(AttributeUserJWT.TYPE_USER);
                Status status = this.statusService.insert(user_id, typeUser, StatusEnum.ON, System.currentTimeMillis());
                if (typeUser.equals(Roles.STUDENT)) {
                    client.joinRoom(user_id);
                    List<Group> groups = this.groupService.getGroups(user_id, typeUser, 0, 0);
                    groups.forEach(group -> {
                        this.namespace.getRoomOperations(group.getUniversity().getId().toString()).sendEvent(ActiveStatus, status);
                    });

                } else {
                    Counselor counselor = this.counselorService.getCounselorById(user_id);
                    client.joinRoom(counselor.getUniversityId().toString());
                    List<Group> groups = this.groupService.getGroups(counselor.get_id().toString(), typeUser, 0, 0);
                    groups.forEach(group -> {
                        this.namespace.getRoomOperations(group.getStudent().getId().toString()).sendEvent(ActiveStatus, status);
                    });
                }

        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            logger.debug("Client[{}] - Disconnected form chat module", client.getSessionId().toString());
            HandshakeData handshakeData = client.getHandshakeData();
            String user_id = handshakeData.getHttpHeaders().get(AttributeUserJWT.USER_ID);
            String typeUser = handshakeData.getHttpHeaders().get(AttributeUserJWT.TYPE_USER);
            Status status = this.statusService.insert(user_id, typeUser, StatusEnum.OFF, System.currentTimeMillis());
            if (typeUser.equals(Roles.STUDENT)) {
                List<Group> groups = this.groupService.getGroups(user_id, typeUser,0,0);
                groups.forEach(group -> {
                    this.namespace.getRoomOperations(group.getUniversity().getId().toString()).sendEvent(ActiveStatus, status);
                });
            } else {
                Counselor counselor = this.counselorService.getCounselorById(user_id);
                List<Group> groups = this.groupService.getGroups(counselor.get_id().toString(), typeUser,0,0);
                groups.forEach(group -> {
                    this.namespace.getRoomOperations(group.getStudent().getId().toString()).sendEvent(ActiveStatus, status);
                });
            }
        };
    }

    private DataListener<MessageRequest> onMessageReceived() {
        return ((client, data, ackSender) -> {
            Message message = this.messageService.insertMessage(data);
            Group group = this.groupService
                    .getGroupByID(data.getGroupId().toString());
            var university_id = group.getUniversity().getId().toString();
            var student_id = group.getStudent().getId().toString();
            var room_student = this.namespace.getRoomOperations(student_id);
            room_student.sendEvent(MessageNameEvent, message);
            if (message.getIsCounselor()) {

                if (room_student.getClients().size() <= 0) {
                    //notification student here
                    this.notificationService.pushNotification(Notification.builder()
                            .message(message.getContentMessage())
                            .targetId(message.getGroupId())
                            .typeOfNotification("message")
                            .image(group.getUniversity().getUriAvatar())
                            .userId(new ObjectId(student_id))
                            .title(group.getUniversity().getFullName())
                            .build());
                }
            }
            var room_university = this.namespace.getRoomOperations(university_id);
            room_university.sendEvent(MessageNameEvent,message);
            if (!message.getIsCounselor() && room_university.getClients().size() == 0) {
                //notification Counselor here
                this.notificationService.pushNotification(Notification.builder()
                        .message(message.getContentMessage())
                        .targetId(message.getGroupId())
                        .typeOfNotification("message")
                        .userId(new ObjectId(university_id))
                        .title(group.getStudent().getFullName())
                        .build());
            }
        });
    }

    private DataListener<Typing> onTypingReceived() {
        return ((client, data, ackSender) -> {
            logger.info(data.toString());
            Group group = this.groupService
                    .getGroupByID(data.getGroupId());
            this.namespace.getRoomOperations(group.getUniversity().getId().toString())
                    .sendEvent(TypingEvent, client, data);
            this.namespace.getRoomOperations(group.getStudent().getId().toString())
                    .sendEvent(TypingEvent, client, data);
        });
    }

    private DataListener<ReadMessage> onReadMessage() {
        return ((client, data, ackSender) -> {
            this.messageService.updateUserReadMessage(data);
            Group group = this.groupService
                    .getGroupByID(data.getGroupId());
            this.namespace.getRoomOperations(group.getUniversity().getId().toString())
                    .sendEvent(READ_MESSAGE, client, data);
            this.namespace.getRoomOperations(group.getStudent().getId().toString())
                    .sendEvent(READ_MESSAGE, client, data);
        });
    }
}
