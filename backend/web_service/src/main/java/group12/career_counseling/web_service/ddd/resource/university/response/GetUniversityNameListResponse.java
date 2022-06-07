package group12.career_counseling.web_service.ddd.resource.university.response;


import group12.career_counseling.web_service.utils.rest.PaginationResponse;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUniversityNameListResponse extends PaginationResponse {
    List<Item> items;

    public GetUniversityNameListResponse(List<Item> items, long page_number, long page_size, long total) {
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
    }
}
