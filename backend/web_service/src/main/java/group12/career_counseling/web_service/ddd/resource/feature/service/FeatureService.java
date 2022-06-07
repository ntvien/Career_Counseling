package group12.career_counseling.web_service.ddd.resource.feature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService implements IFeatureService {
    @Autowired
    IFeatureService featureService;
}
