package group12.career_counseling.web_service.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class QueryFactory {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Document buildQuerySet(@NonNull Object object) {
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> data = objectMapper.convertValue(object, typeRef);
        Document queryItem = new Document();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() != null) {
                queryItem.put(entry.getKey(), entry.getValue());
            }
        }
        Document query = new Document();
        queryItem.remove("_id");
        query.put("$set", queryItem);
        return query;
    }
}
