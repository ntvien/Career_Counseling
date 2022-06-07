package group12.career_counseling.web_service.ddd.community.share.service;

import group12.career_counseling.web_service.ddd.community.share.Share;
import group12.career_counseling.web_service.ddd.community.share.request.ShareRequest;
import org.springframework.stereotype.Service;

@Service
public interface IShareService {
    Share insertShare(ShareRequest request);
    Long countShare(String ResourceId);
}
