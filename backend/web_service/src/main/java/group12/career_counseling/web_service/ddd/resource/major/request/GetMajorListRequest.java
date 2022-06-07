package group12.career_counseling.web_service.ddd.resource.major.request;

import group12.career_counseling.web_service.utils.rest.PaginationRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetMajorListRequest extends PaginationRequest {
    private String keyword;
    private String sortBy;
}
