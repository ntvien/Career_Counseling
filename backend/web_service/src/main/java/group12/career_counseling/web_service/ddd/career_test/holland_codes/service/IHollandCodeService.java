package group12.career_counseling.web_service.ddd.career_test.holland_codes.service;

import group12.career_counseling.web_service.ddd.career_test.holland_codes.HollandCode;

import java.util.List;

public interface IHollandCodeService {
    void uploadFile(String fileName) throws Exception;

    List<HollandCode> getHollandCodeList() throws Exception;
}
