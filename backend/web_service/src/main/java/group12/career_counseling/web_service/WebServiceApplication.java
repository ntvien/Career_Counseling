package group12.career_counseling.web_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.controller.*;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.HollandCodeController;
import group12.career_counseling.web_service.ddd.career_test.holland_questions.HollandQuestionController;
import group12.career_counseling.web_service.ddd.chat.group.GroupsController;
import group12.career_counseling.web_service.ddd.chat.message.MessageController;
import group12.career_counseling.web_service.ddd.community.comment.CommentController;
import group12.career_counseling.web_service.ddd.community.like.LikeController;
import group12.career_counseling.web_service.ddd.notification.user_device.UserDeviceController;
import group12.career_counseling.web_service.ddd.resource.advertising_images.AdvertisingImage;
import group12.career_counseling.web_service.ddd.resource.advertising_images.AdvertisingImageController;
import group12.career_counseling.web_service.ddd.resource.major.MajorController;
import group12.career_counseling.web_service.ddd.resource.major.service.MajorService;
import group12.career_counseling.web_service.ddd.resource.university.UniversityController;
import group12.career_counseling.web_service.ddd.user.UserController;
import group12.career_counseling.web_service.ddd.user.counselor.CounselorController;
import group12.career_counseling.web_service.ddd.user.student.StudentController;
import group12.career_counseling.web_service.vertx.VertxProvider;
import group12.career_counseling.web_service.vertx.rest.RESTfulVerticle;
import group12.career_counseling.web_service.vertx.rest.RequestHandler;
import group12.career_counseling.web_service.vertx.rest.authentication.Roles;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class WebServiceApplication {

    @Autowired
    private RESTfulVerticle resTfulVerticle;
    @Autowired
    private BaseController baseController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private GroupsController groupsController;
    @Autowired
    private MessageController messageController;
    @Autowired
    private CounselorController counselorController;
    @Autowired
    private UniversityController universityController;
    @Autowired
    private UserController userController;
    @Autowired
    private UserDeviceController userDeviceController;
    @Autowired
    private AdvertisingImageController advertisingImageController;
    @Autowired
    private MajorController majorController;
    @Autowired
    private Vertx vertx;
    @Autowired
    private CommentController commentController;
    @Autowired
    private LikeController likeController;
    @Autowired
    private HollandCodeController hollandCodeController;
    @Autowired
    private HollandQuestionController hollandQuestionController;
    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    Vertx vertx() {
        return Vertx.vertx();
    }

    @PostConstruct
    public void deployAPI() {
        resTfulVerticle.setRequestHandlers(Arrays.asList(
                RequestHandler.init(HttpMethod.GET, "/index", baseController::index, false),
                RequestHandler.init(HttpMethod.GET, "/user/profile", userController::getUserProfile, true, Roles.STUDENT),
                RequestHandler.init(HttpMethod.POST, "/user/refresh-token", userController::refreshToken, false),
                RequestHandler.init(HttpMethod.POST, "/user/add-device", userDeviceController::addUserDevice, true),
                RequestHandler.init(HttpMethod.POST, "/user/remove-device", userDeviceController::removeUserDevice, true),
                // user/students
                RequestHandler.init(HttpMethod.POST, "/user/students/register", studentController::register, false),
                RequestHandler.init(HttpMethod.POST, "/user/students/login", studentController::login, false),
                RequestHandler.init(HttpMethod.GET, "/user/students/profile", studentController::getProfile, true, Roles.STUDENT),
                 // user/counselors
                RequestHandler.init(HttpMethod.POST, "/user/counselors/register", counselorController::register, false),
                RequestHandler.init(HttpMethod.POST, "/user/counselors/login", counselorController::login, false),
                RequestHandler.init(HttpMethod.GET, "/user/counselors/profile", counselorController::getProfile, true, Roles.COUNSELOR),
                RequestHandler.init(HttpMethod.GET, "/chat/groups", groupsController::getGroups, true, Roles.COUNSELOR, Roles.STUDENT),
                RequestHandler.init(HttpMethod.GET, "/chat/groups/:groupid/messages", messageController::getMessagesByGroupId, true, Roles.COUNSELOR, Roles.STUDENT),
                // resource/universities
                RequestHandler.init(HttpMethod.GET, "/resource/universities/name", universityController::getUniversityNameList, false),
                RequestHandler.init(HttpMethod.POST, "/resource/universities/", universityController::createUniversity, true, Roles.STUDENT, Roles.COUNSELOR),
                RequestHandler.init(HttpMethod.GET, "/resource/universities/:_id", universityController::getUniversityById, false),
                RequestHandler.init(HttpMethod.GET, "/resource/universities", universityController::getUniversityList, false),
                RequestHandler.init(HttpMethod.PUT, "/resource/universities/:_id/view-number", universityController::increaseView, false),

                //dev
                RequestHandler.init(HttpMethod.POST, "/resource/universities/upload", universityController::uploadFile, false),
                // resource/majors
                RequestHandler.init(HttpMethod.GET, "/resource/majors", majorController::getMajorList, false),
                RequestHandler.init(HttpMethod.GET, "/resource/majors/all", majorController::getAllMajor, false),
                RequestHandler.init(HttpMethod.GET, "/resource/majors/:_id", majorController::getMajor, false),
                RequestHandler.init(HttpMethod.PUT, "/resource/majors/:_id/view-number", majorController::increaseView, false),
                RequestHandler.init(HttpMethod.POST, "/resource/majors/handle-dataset", majorController::handleDataset, false),
                //dev
                RequestHandler.init(HttpMethod.POST, "/resource/majors/upload", majorController::uploadFile, false),


                // resource/advertising-images
                RequestHandler.init(HttpMethod.GET, "/resource/advertising-images", advertisingImageController::getAdvertisingImageList, false),


                /// community
                RequestHandler.init(HttpMethod.POST, "/community/comments", commentController::insert, true),
                RequestHandler.init(HttpMethod.GET, "/community/comments", commentController::getComments, false),
                RequestHandler.init(HttpMethod.POST, "/community/comments/evaluate", commentController::updateEvaluateComment, true),

                RequestHandler.init(HttpMethod.POST, "/community/likes", likeController::insert, true),
                RequestHandler.init(HttpMethod.GET, "/community/likes", likeController::countLike, false),
                RequestHandler.init(HttpMethod.GET, "/community/likes/check", likeController::checkUserLike, true),

                RequestHandler.init(HttpMethod.POST, "/community/shares", likeController::insert, true),
                RequestHandler.init(HttpMethod.GET, "/community/shares", likeController::countLike, false),
                // career-test/holland_codes
                RequestHandler.init(HttpMethod.POST, "/career-test/holland-codes/upload", hollandCodeController::uploadFile, false),
                RequestHandler.init(HttpMethod.GET, "/career-test/holland-codes", hollandCodeController::getHollandCodeList, false),
                // career-test/holland_questions
                RequestHandler.init(HttpMethod.GET, "/career-test/holland-questions", hollandQuestionController::getHollandQuestionList, false),
                RequestHandler.init(HttpMethod.POST, "/career-test/holland-questions/upload", hollandQuestionController::uploadFile, false)

                ));
        vertx.deployVerticle(resTfulVerticle);
    }


}
