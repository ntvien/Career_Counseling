package group12.career_counseling.web_service.ddd.career_test.holland_questions;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.service.IHollandCodeService;
import group12.career_counseling.web_service.ddd.career_test.holland_questions.service.IHollandQuestionService;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class HollandQuestionController {
    @Autowired
    private IHollandQuestionService hollandQuestionService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    public void uploadFile(RoutingContext routingContext) {
        try {
            Set<FileUpload> uploads = routingContext.fileUploads();
            for (FileUpload fileUpload : uploads) {
                hollandQuestionService.uploadFile(fileUpload.uploadedFileName());
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end();
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void getHollandQuestionList(RoutingContext routingContext) {
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(hollandQuestionService.getHollandQuestionList(routingContext.request().params().get("codeId"))));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
}
