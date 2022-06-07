package group12.career_counseling.web_service.ddd.career_test.holland_questions.service;

import group12.career_counseling.web_service.ddd.career_test.holland_codes.HollandCode;
import group12.career_counseling.web_service.ddd.career_test.holland_questions.HollandQuestion;

import java.util.List;

public interface IHollandQuestionService {
    void uploadFile(String fileName) throws Exception;

    List<HollandQuestion> getHollandQuestionList(String codeId) throws Exception;
}
