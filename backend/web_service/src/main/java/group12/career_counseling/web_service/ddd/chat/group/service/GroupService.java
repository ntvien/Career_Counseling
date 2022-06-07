package group12.career_counseling.web_service.ddd.chat.group.service;

import group12.career_counseling.web_service.ddd.chat.group.Group;
import group12.career_counseling.web_service.ddd.chat.group.repository.IGroupRepository;
import group12.career_counseling.web_service.ddd.chat.message.Message;
import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.ddd.user.student.Student;
import group12.career_counseling.web_service.ddd.user.student.service.IStudentService;
import group12.career_counseling.web_service.utils.MyStringUtils;
import group12.career_counseling.web_service.utils.enumeration.chat.MemberGroupNameField;
import group12.career_counseling.web_service.utils.enumeration.chat.MessageNameField;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.utils.enumeration.chat.GroupNameField;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IUniversityService universityService;
    @Autowired
    private ICounselorService counselorService;

    @Override
    public Group getGroupByID(String id) {
        return this.groupRepository
                .getById(id)
                .orElseThrow(() -> new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.GroupIdNotFoundException));
    }

    @Override
    public Group CreateGroup(Group group) {
        this.studentService.getStudentById(group.getStudent().getId().toString());
        this.universityService.getUniversityById(group.getUniversity().getId().toString());
        return this.groupRepository.insert(group).orElseThrow(() ->
                new HttpException(StatusCodes.INTERNAL_SERVER_ERROR, ExceptionMessages.GroupInsertException));
    }

    @Override
    public Group CreateGroup(String university_id, String student_id) {
        Student student = this.studentService.getStudentById(student_id);
        University university = this.universityService.getUniversityById(university_id);
        return this.groupRepository
                .getByQuery(new Document()
                        .append(GroupNameField.STUDENT + '.' + MemberGroupNameField.ID, new ObjectId(student_id))
                        .append(GroupNameField.UNIVERSITY + '.' + MemberGroupNameField.ID, new ObjectId(university_id)))
                .or(() -> this.groupRepository.insert(Group
                        .builder()
                        .student(Group.MemberOfGroup.builder()
                                .id(student.get_id())
                                .fullName(student.getFullName())
                                .fullNameUnsigned(MyStringUtils.removeUnicode(student.getFullName()))
                                .build())
                        .university(Group.MemberOfGroup.builder()
                                .fullName(university.getName())
                                .fullNameUnsigned(university.getNameUnsigned())
                                .id(university.get_id())
                                .uriAvatar(university.getLogo())
                                .build())
                        .build())).orElseThrow(() ->
                        new HttpException(StatusCodes.INTERNAL_SERVER_ERROR, ExceptionMessages.GroupInsertException));

    }

    @Override
    public List<Group> getGroups(String user_id, String role, int offset, int limit) {
        String field_id = role.equals(Roles.STUDENT) ? GroupNameField.STUDENT : GroupNameField.UNIVERSITY;
        ObjectId id = new ObjectId(user_id);
        if (role.equals(Roles.COUNSELOR)) {
            Counselor counselor = counselorService.getCounselorById(user_id);
            id = counselor.getUniversityId();
        }

        return this.groupRepository.getMany(new Document()
                        .append(field_id + '.' + MemberGroupNameField.ID, id),
                new Document()
                        .append(GroupNameField.LAST_MESSAGE + "." + MessageNameField.CREATE_TIME, -1),
                offset,
                limit).
                orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public List<Group> getAllGroupIdOfMember(String id, String role) {
        String nameFieldId = role.equals(Roles.STUDENT) ?
                GroupNameField.STUDENT :
                GroupNameField.UNIVERSITY + '.' + MemberGroupNameField.ID;
        return this.groupRepository.getMany(
                new Document(nameFieldId, id),
                new Document(),
                new Document()
                        .append(GroupNameField.STUDENT + '.' + MemberGroupNameField.ID, 1)
                        .append(GroupNameField.UNIVERSITY + '.' + MemberGroupNameField.ID, 1),
                0,
                0)
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Boolean updateLastMessage(String groupId, Group.LastMessage lastMessage) {
        return this.groupRepository.update(groupId, Group
                .builder()
                .lastMessage(lastMessage)
                .build())
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Boolean updateUserReadLastMessage(String groupId, String userIdRead) {
        return this.groupRepository.updateUserReadLastMessage(groupId, userIdRead);
    }

    @Override
    public List<Group> getGroupByNames(String user_id, String role, String pattern) {
        return Collections.emptyList();
    }

}
