package group12.career_counseling.web_service.ddd.resource.major.service;

import group12.career_counseling.web_service.ddd.resource.major.Major;
import group12.career_counseling.web_service.ddd.resource.major.request.GetAllMajorRequest;
import group12.career_counseling.web_service.ddd.resource.major.request.GetMajorListRequest;
import group12.career_counseling.web_service.ddd.resource.major.response.GetMajorListResponse;
import group12.career_counseling.web_service.ddd.resource.major.response.GetMajorResponse;

import java.util.List;
import java.util.Optional;

public interface IMajorService {
    void uploadFile(String fileName) throws Exception;

    GetMajorListResponse getMajorList(GetMajorListRequest getMajorListRequest) throws Exception;

    List<GetMajorListResponse.Item> getAllMajor(GetAllMajorRequest getAllMajorRequest) throws Exception;

    GetMajorResponse getMajorById(String _id) throws Exception;

    void increaseView(String _id);

    //dev
    void updateLike();

    void handleDataset(String fileName) throws Exception;
}
