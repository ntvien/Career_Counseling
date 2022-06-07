package group12.career_counseling.web_service.ddd.resource.university.response;

import group12.career_counseling.web_service.utils.rest.PaginationResponse;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetUniversityListResponse extends PaginationResponse {
    List<Item> items;

    public GetUniversityListResponse(List<Item> items, long page_number, long page_size, long total) {
        super(page_number, page_size, total);
        this.items = items;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String _id;
        private String name;
        private String image;
        private String logo;
        private Integer starNumber;
        private Integer viewNumber;
    }
}
