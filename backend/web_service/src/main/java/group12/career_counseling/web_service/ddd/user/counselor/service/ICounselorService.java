package group12.career_counseling.web_service.ddd.user.counselor.service;

import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.request.LoginRequest;
import group12.career_counseling.web_service.ddd.user.counselor.request.RegisterRequest;
import group12.career_counseling.web_service.ddd.user.counselor.response.ProfileResponse;
import group12.career_counseling.web_service.vertx.rest.authentication.AuthenticationResponse;

public interface ICounselorService {
    Counselor getCounselorById(String id);

    Boolean updateStatus(String id, String status, long timeAt);

    Counselor createCounselor(RegisterRequest registerRequest) throws Exception;

    AuthenticationResponse authenticate(LoginRequest authenticationRequest) throws Exception;

    ProfileResponse getProfile(String _id) throws Exception;

}
