package group12.career_counseling.web_service.ddd.career_test.holland_codes;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.service.IHollandCodeService;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class HollandCodeController {
    @Autowired
    private IHollandCodeService hollandCodeService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    public void uploadFile(RoutingContext routingContext) {
        try {
            Set<FileUpload> uploads = routingContext.fileUploads();
            for (FileUpload fileUpload : uploads) {
                hollandCodeService.uploadFile(fileUpload.uploadedFileName());
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end();
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void getHollandCodeList(RoutingContext routingContext) {
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(hollandCodeService.getHollandCodeList()));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
}
