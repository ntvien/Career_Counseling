package group12.career_counseling.web_service.ddd.resource.advertising_images.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.resource.advertising_images.AdvertisingImage;
import group12.career_counseling.web_service.ddd.resource.advertising_images.repository.IAdvertisingImageRepository;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.utils.enumeration.resource.UniversityNameField;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisingImageService implements IAdvertisingImageService {
    @Autowired
    private IAdvertisingImageRepository advertisingImageRepository;


    @Override
    public List<AdvertisingImage> getAdvertisingImageList() {
        return advertisingImageRepository.getAll(new Document(), new Document("position", 1)).orElse(new ArrayList<>());
    }
}
