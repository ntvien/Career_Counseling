package group12.career_counseling.web_service.ddd.resource.university.service;

import group12.career_counseling.web_service.ddd.resource.major.response.GetMajorListResponse;
import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityListRequest;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityNameListRequest;
import group12.career_counseling.web_service.ddd.resource.university.response.GetUniversityListResponse;
import group12.career_counseling.web_service.ddd.resource.university.response.GetUniversityNameListResponse;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IUniversityService {

    University getUniversityById(String id);

    University getUniversityById(ObjectId _id);

    University GetUniversityByCounselorId(String id);

    University CreateUniversity(University university);

    List<University> getUniversities(int offset, int limit);

    List<String> getListNameUniversities();

    void uploadFile(String fileName) throws Exception;

    List<ObjectId> getIdsFromNumbers(List<Integer> numbers);

    GetUniversityListResponse getUniversityList(GetUniversityListRequest getUniversityListRequest) throws Exception;

    List<University> getUniversityList(List<ObjectId> universities) throws Exception;

    GetUniversityNameListResponse getUniversityNameList(GetUniversityNameListRequest getUniversityNameListRequest) throws Exception;

    void increaseView(String universityId);
}
