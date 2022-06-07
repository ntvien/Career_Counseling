package group12.career_counseling.web_service.ddd.chat.status.services;

import group12.career_counseling.web_service.ddd.chat.status.Status;

import java.util.List;

public interface IStatusService {

    List<Status> getStatus(String  userId, int page ,int size);
    Status insert(String userId,String role, String status,long timeAt);
}
