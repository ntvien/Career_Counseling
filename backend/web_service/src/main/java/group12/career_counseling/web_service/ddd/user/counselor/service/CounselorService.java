package group12.career_counseling.web_service.ddd.user.counselor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.repository.ICounselorRepository;
import group12.career_counseling.web_service.ddd.user.counselor.request.LoginRequest;
import group12.career_counseling.web_service.ddd.user.counselor.request.RegisterRequest;
import group12.career_counseling.web_service.ddd.user.counselor.response.ProfileResponse;
import group12.career_counseling.web_service.utils.MyStringUtils;
import group12.career_counseling.web_service.utils.enumeration.chat.StatusEnum;
import group12.career_counseling.web_service.vertx.rest.authentication.AuthenticationResponse;
import group12.career_counseling.web_service.vertx.rest.authentication.JWTClaim;
import group12.career_counseling.web_service.vertx.rest.authentication.MyJWTAuthentication;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CounselorService implements ICounselorService {
    @Autowired
    private ICounselorRepository counselorRepository;
    @Autowired
    private IUniversityService universityService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MyJWTAuthentication jwtAuthentication;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Counselor getCounselorById(String id) {
        return this.counselorRepository.getById(id).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Boolean updateStatus(String id, String status, long timeAt) {
        return this.counselorRepository.update(id, Counselor
                .builder()
                .lastStatus(Counselor.Status
                        .builder()
                        .status(status)
                        .timeAt(timeAt)
                        .build()).build()).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public Counselor createCounselor(RegisterRequest registerRequest) throws Exception {
        if (StringUtils.isAnyBlank(registerRequest.getEmail(), registerRequest.getPhone())) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.REQUIRED_FIELDS_ARE_NULL);
        }
        if (registerRequest.getPhone().length() < 9 || registerRequest.getPhone().length() > 10 ||
                registerRequest.getPhone().charAt(0) == '0' && registerRequest.getPhone().length() < 10) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.INVALID_PHONE);
        }
        if (!registerRequest.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.INVALID_EMAIL);
        }
        ;
        String phone = MyStringUtils.formatE164Phone(registerRequest.getPhone());
        Document query = new Document()
                .append("phone", phone);
        counselorRepository.getByQuery(query).ifPresent((value) -> {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.PHONE_EXISTED);
        });
        universityService.getUniversityById(registerRequest.getUniversityId());
        CreateRequest request = new CreateRequest()
                .setPhoneNumber(phone);
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        Counselor counselor = Counselor.builder()
                .universityId(new ObjectId(registerRequest.getUniversityId()))
                .email(registerRequest.getEmail())
                .firebaseId(userRecord.getUid())
                .phone(phone)
                .lastStatus(Counselor.Status.builder()
                        .status(StatusEnum.OFF)
                        .timeAt(System.currentTimeMillis())
                        .build())
                .build();
        return counselorRepository.insert(counselor).orElseThrow(() ->
                new HttpException(StatusCodes.INTERNAL_SERVER_ERROR)
        );
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest authenticationRequest) throws Exception {
        if (StringUtils.isAnyBlank(authenticationRequest.getToken())) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.REQUIRED_FIELDS_ARE_NULL);
        }
        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(authenticationRequest.getToken());
        String uid = decoded.getUid();
        Document query = new Document("firebaseId", uid);
        Counselor counselor = counselorRepository.getByQuery(query).orElseThrow(() ->
                new HttpException(StatusCodes.NOT_FOUND)
        );
        CompletableFuture.runAsync(() -> {
            Counselor update_counselor = Counselor.builder()
                    .lastStatus(Counselor.Status.builder()
                            .status(StatusEnum.ON)
                            .timeAt(System.currentTimeMillis())
                            .build())
                    .build();
            counselorRepository.update(counselor.get_id().toHexString(), update_counselor);
        });

        JWTClaim jwtClaim = JWTClaim.builder()
                .userId(counselor.get_id().toHexString())
                .groups(List.of(Roles.COUNSELOR))
                .build();
        return jwtAuthentication.generateToken(jwtClaim);
    }

    @Override
    public ProfileResponse getProfile(String _id) throws Exception {
        Counselor counselor = counselorRepository.getById(_id).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
        ProfileResponse result = modelMapper.map(counselor, ProfileResponse.class);
        University university = universityService.getUniversityById(counselor.getUniversityId());
        result.setUniversity(university);
        return result;
    }
}
