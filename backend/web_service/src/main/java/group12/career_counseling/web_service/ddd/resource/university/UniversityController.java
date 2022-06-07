package group12.career_counseling.web_service.ddd.resource.university;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.resource.major.request.GetMajorListRequest;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityListRequest;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityNameListRequest;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import group12.career_counseling.web_service.utils.enumeration.authentication.AttributeUserJWT;
import group12.career_counseling.web_service.utils.enumeration.authentication.ClaimFieldName;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class UniversityController {
    @Autowired
    private IUniversityService universityService;
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    public void createUniversity(RoutingContext routingContext) {
        try {
            University university = JSON.parseObject(routingContext.getBodyAsString(), University.class);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(universityService.CreateUniversity(university)));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void getUniversityById(RoutingContext routingContext) {
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(universityService.getUniversityById(
                            routingContext.pathParam("_id")
                    )));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void getUniversities(RoutingContext routingContext) {
        try {
            if (Boolean.parseBoolean(routingContext.queryParam("isself").get(0))) {
                String counselor_id = routingContext.user().principal().getString(ClaimFieldName.USER_ID);
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json; charset=utf-8")
                        .end(objectMapper.writeValueAsString(universityService.GetUniversityByCounselorId(counselor_id)));
            } else {
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json; charset=utf-8")
                        .end(objectMapper.writeValueAsString(universityService.getUniversities(
                                Integer.parseInt(routingContext.queryParam("offset").get(0)),
                                Integer.parseInt(routingContext.queryParam("limit").get(0)))
                        ));
            }
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void getListNameUniversity(RoutingContext routingContext) {
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(universityService.getListNameUniversities()));
        } catch (Throwable throwable) {
            routingContext.fail(throwable);
        }
    }

    public void uploadFile(RoutingContext routingContext) {
        try {
            Set<FileUpload> uploads = routingContext.fileUploads();
            for (FileUpload fileUpload : uploads) {
                universityService.uploadFile(fileUpload.uploadedFileName());
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end();
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }

    public void getUniversityList(RoutingContext routingContext) {
        try {
            MultiMap params = routingContext.request().params();
            GetUniversityListRequest request = GetUniversityListRequest.builder()
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
                    .end(objectMapper.writeValueAsString(universityService.getUniversityList(request)));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
    public void getUniversityNameList(RoutingContext routingContext) {
        try {
            MultiMap params = routingContext.request().params();
            GetUniversityNameListRequest request = GetUniversityNameListRequest.builder().build();
            if (StringUtils.isNotBlank(params.get("page"))) {
                request.setPage(Integer.parseInt(params.get("page")));
            }
            if (StringUtils.isNotBlank(params.get("size"))) {
                request.setSize(Integer.parseInt(params.get("size")));
            }
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(objectMapper.writeValueAsString(universityService.getUniversityNameList(request)));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
    public void increaseView(RoutingContext routingContext) {
        try {
            String _id = routingContext.pathParam("_id");
            this.universityService.increaseView(_id);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json; charset=utf-8")
                    .end(String.valueOf(true));
        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
}
