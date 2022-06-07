package group12.career_counseling.web_service.ddd.resource.major.repository;

import group12.career_counseling.web_service.ddd.resource.major.Major;
import group12.career_counseling.web_service.mongodb.generic.IGenericRepository;

public interface IMajorRepository extends IGenericRepository<Major> {
    void updateView(String id, int i);
}
