package group12.career_counseling.web_service.ddd.chat.group.response;

import group12.career_counseling.web_service.utils.rest.PaginationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetGroupListResponse extends PaginationResponse {
    List<Item> items;

    public GetGroupListResponse(List<Item> items, long page_number, long page_size, long total) {
        super(page_number, page_size, total);
        this.items = items;
    }

    public static class Item {

    }
}
