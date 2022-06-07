package group12.career_counseling.web_service.ddd.chat.message.service;

import group12.career_counseling.web_service.ddd.chat.group.Group;
import group12.career_counseling.web_service.ddd.chat.group.service.IGroupService;
import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.ddd.chat.message.repository.IMessageRepository;
import group12.career_counseling.web_service.ddd.chat.message.request.MessageRequest;
import group12.career_counseling.web_service.ddd.chat.message.request.ReadMessage;
import group12.career_counseling.web_service.utils.enumeration.chat.GroupNameField;
import group12.career_counseling.web_service.utils.enumeration.chat.MessageNameField;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;
    @Autowired
    private IGroupService groupService;

    @Override
    public Message insertMessage(MessageRequest messageRequest) {
        Message message = Message.builder()
                .contentMessage(messageRequest.getContentMessage())
                .groupId(new ObjectId(messageRequest.getGroupId()))
                .userId(new ObjectId(messageRequest.getUserId()))
                .isCounselor(messageRequest.getIsCounselor()).build();
        message.setCreatedTime(messageRequest.getCreatedTime());
        Message messageResult = this.messageRepository.insert(message).orElseThrow(() ->
                new HttpException(StatusCodes.INTERNAL_SERVER_ERROR, ExceptionMessages.InsertMessageFalse));
        this.groupService.updateLastMessage(
                messageRequest.getGroupId(),
                Group.LastMessage
                        .builder()
                        .userId(messageResult.getUserId().toString())
                        .contentMessage(messageResult.getContentMessage())
                        .isCounselor(messageResult.getIsCounselor())
                        .createdTime(messageResult.getCreatedTime())
                        .build());
        return messageResult;
    }

    @Override
    public List<Message> getMessageByGroupId(String groupId, int offset, int limit) {
        return this.messageRepository.getMany(new Document()
                        .append(MessageNameField.GROUP_ID, new ObjectId(groupId)),
                new Document()
                        .append(MessageNameField.CREATE_TIME, -1),
                offset,
                limit).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Boolean updateUserReadMessage(ReadMessage readMessage) {

        this.groupService.updateUserReadLastMessage(readMessage.getGroupId(),
                readMessage.getUserId());
        return this.messageRepository.updateUserReadMessage(
                readMessage.getMessageId(),
                Message.UserRead.builder()
                        .userId(new ObjectId(readMessage.getUserId()))
                        .createdTime(readMessage.getCreatedTime())
                        .build());
    }
}
