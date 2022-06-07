package group12.career_counseling.web_service.ddd.user;

import group12.career_counseling.web_service.ddd.user.student.request.RefreshTokenRequest;
import group12.career_counseling.web_service.vertx.rest.authentication.AuthenticationResponse;
import io.vertx.ext.web.handler.HttpException;

interface IUserService {
    User getUserProfile(String userId, String role);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws HttpException;
}
