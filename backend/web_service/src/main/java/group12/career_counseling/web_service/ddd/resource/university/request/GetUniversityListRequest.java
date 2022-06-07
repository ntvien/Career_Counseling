package group12.career_counseling.web_service.ddd.resource.university.request;

import group12.career_counseling.web_service.utils.rest.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetUniversityListRequest extends PaginationRequest {
    private String keyword;
    private String sortBy;
}
