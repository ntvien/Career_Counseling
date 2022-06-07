package group12.career_counseling.web_service.utils.rest;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class PaginationRequest {
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 20;
}
