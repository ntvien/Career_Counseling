package group12.career_counseling.web_service.ddd.resource.major.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllMajorRequest {
    private List<String> ids = new ArrayList<>();
}
