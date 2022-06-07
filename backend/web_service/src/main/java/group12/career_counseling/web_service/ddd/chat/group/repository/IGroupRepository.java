package group12.career_counseling.web_service.ddd.chat.group.repository;

import group12.career_counseling.web_service.ddd.chat.group.Group;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;

public interface IGroupRepository extends IGenericRepository<Group> {
    Boolean updateUserReadLastMessage(String groupId, String userIdRead);
}
