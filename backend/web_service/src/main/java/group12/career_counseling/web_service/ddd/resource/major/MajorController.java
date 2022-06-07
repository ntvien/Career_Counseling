package group12.career_counseling.web_service.ddd.resource.major;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.resource.major.request.GetAllMajorRequest;
import group12.career_counseling.web_service.ddd.resource.major.request.GetMajorListRequest;
import group12.career_counseling.web_service.ddd.resource.major.service.IMajorService;
import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
public class MajorController {
    @Autowired
    private IMajorService majorService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    public void uploadFile(RoutingContext routingContext) {
        try {
            Set<FileUpload> uploads = routingContext.fileUploads();
            for (FileUpload fileUpload : uploads) {
                majorService.uploadFile(fileUpload.uploadedFileName());
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end();
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void handleDataset(RoutingContext routingContext) {
        try {
            Set<FileUpload> uploads = routingContext.fileUploads();
            for (FileUpload fileUpload : uploads) {
                majorService.handleDataset(fileUpload.uploadedFileName());
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end();
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void getMajorList(RoutingContext routingContext) {
        try {
            MultiMap params = routingContext.request().params();
            GetMajorListRequest request = GetMajorListRequest.builder()
                    .keyword(params.get("keyword"))
                    .sortBy(params.get("sortBy"))
                    .build();
            if (StringUtils.isNotBlank(params.get("page"))) {
                request.setPage(Integer.parseInt(params.get("page")));
            }
            if (StringUtils.isNotBlank(params.get("size"))) {
                request.setSize(Integer.parseInt(params.get("size")));
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(majorService.getMajorList(request)));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void getAllMajor(RoutingContext routingContext) {
        try {
            MultiMap params = routingContext.request().params();
            GetAllMajorRequest request = GetAllMajorRequest.builder()
                    .build();
            if (StringUtils.isNoneBlank(params.get("ids"))) {
                request.setIds(List.of(params.get("ids").split(",")));
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(majorService.getAllMajor(request)));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void getMajor(RoutingContext routingContext) {
        try {
            String _id = routingContext.pathParam("_id");
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(majorService.getMajorById(_id)));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void increaseView(RoutingContext routingContext) {
        try {
            String _id = routingContext.pathParam("_id");
            this.majorService.increaseView(_id);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(String.valueOf(true));
            ;
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
}
