package group12.career_counseling.web_service.ddd.chat.message.service;

import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.ddd.chat.message.Reaction;
import group12.career_counseling.web_service.ddd.chat.message.request.MessageRequest;
import group12.career_counseling.web_service.ddd.chat.message.request.ReadMessage;

import java.util.List;

public interface IMessageService {
    Message insertMessage(MessageRequest message);
    List<Message> getMessageByGroupId(String groupId, int offset, int limit);
    Boolean updateUserReadMessage(ReadMessage readMessage);
}
