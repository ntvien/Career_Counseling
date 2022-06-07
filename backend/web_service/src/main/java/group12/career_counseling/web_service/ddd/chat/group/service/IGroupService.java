package group12.career_counseling.web_service.ddd.chat.group.service;

import group12.career_counseling.web_service.ddd.chat.group.Group;
import org.bson.types.ObjectId;

import java.util.List;

public interface IGroupService {
    Group getGroupByID(String id);
    Group CreateGroup(Group group);
    Group CreateGroup(String university_id, String student_id);
    List<Group> getGroups(String user_id, String role, int offset, int limti);
    List<Group> getAllGroupIdOfMember(String id,String role);
    Boolean updateLastMessage(String groupId, Group.LastMessage lastMessage);
    Boolean updateUserReadLastMessage(String groupId, String userIdRead);
    List<Group> getGroupByNames(String user_id, String role,String pattern);
}
