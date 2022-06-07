package group12.career_counseling.web_service.ddd.chat.message.repository;

import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;

public interface IMessageRepository extends IGenericRepository<Message> {
    Boolean updateUserReadMessage(String messageId, Message.UserRead userRead);
}
