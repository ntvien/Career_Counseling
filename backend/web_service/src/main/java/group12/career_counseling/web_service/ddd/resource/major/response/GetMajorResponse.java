package group12.career_counseling.web_service.ddd.resource.major.response;

import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityListRequest;
import group12.career_counseling.web_service.ddd.resource.university.response.GetUniversityListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMajorResponse {
    private String _id;
    private String description;
    private String image;
    private String name;
    private Integer starNumber;
    private Integer viewNumber;
    @Builder.Default
    private List<GetUniversityListResponse.Item> universities = new ArrayList<>();
}
