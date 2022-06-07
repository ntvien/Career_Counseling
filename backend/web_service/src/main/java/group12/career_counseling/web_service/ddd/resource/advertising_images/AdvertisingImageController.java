package group12.career_counseling.web_service.ddd.resource.advertising_images;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.resource.advertising_images.service.IAdvertisingImageService;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdvertisingImageController {
    @Autowired
    private IAdvertisingImageService advertisingImageService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;


    public void getAdvertisingImageList(RoutingContext routingContext) {
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(advertisingImageService.getAdvertisingImageList()));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }
}
