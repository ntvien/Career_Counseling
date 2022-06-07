package group12.career_counseling.web_service.mongodb.operator;

import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public interface IMongoDBOperator<Model> {

    Model insert(Model data);

    List<Model> insert(List<Model> data);

    Long count(Document query);

    Model find(Document query, Document sort);

    List<Model> findMany(Document query, Document sort, int skip, int limit);

    List<Model> findMany(Document query, Document sort, Document projection, int skip, int limit);

    List<Model> findAll(Document query, Document sort, Document projection);

    Boolean updateOne(Document query, Document data);

    Boolean updateMany(Document query, Document data);

}
