package group12.career_counseling.web_service.utils.rest;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public abstract class PaginationResponse {

    private long pageNumber;
    private long pageSize;
    private long totalItems;
    private long totalPages;
    private boolean hasNext;
    private long nextPage = 1;
    private boolean hasPrevious;
    private long previousPage = 1;

    public PaginationResponse(long pageNumber, long pageSize, long total) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalItems = total;
        this.totalPages = (int) Math.ceil((double) total / this.pageSize);

        this.hasNext = this.pageNumber < this.totalPages;
        this.hasPrevious = this.pageNumber > 1;

        if (this.hasNext) {
            this.nextPage = this.pageNumber + 1;
        }

        if (this.hasPrevious) {
            this.previousPage = this.pageNumber - 1;
        }
    }
}

