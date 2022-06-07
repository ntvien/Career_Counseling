package group12.career_counseling.web_service.ddd.resource.university.repository;

import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;

public interface IUniversityRepository extends IGenericRepository<University> {
    void updateView(String universityId, long amount );
}
