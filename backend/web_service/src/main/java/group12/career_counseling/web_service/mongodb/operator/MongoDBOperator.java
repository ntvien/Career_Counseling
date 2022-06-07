package group12.career_counseling.web_service.mongodb.operator;

import com.mongodb.client.MongoCollection;
import group12.career_counseling.web_service.mongodb.MongoDBClient;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBOperator<Model> implements IMongoDBOperator<Model> {

    MongoCollection<Model> collection;

    public MongoDBOperator(MongoDBClient mongoDBClient, String dbName, String collectionName, Class<Model> modelClass) {
        collection = mongoDBClient.getClient().getDatabase(dbName).getCollection(collectionName, modelClass);
    }

    @Override
    public Model insert(Model data) {
        collection.insertOne(data);
        return data;
    }

    @Override
    public List<Model> insert(List<Model> data) {
        collection.insertMany(data);
        return data;
    }

    @Override
    public Long count(Document query) {
        return collection.countDocuments(query);
    }

    @Override
    public Model find(Document query, Document sort) {
        return collection.find(query).sort(sort).first();
    }

    @Override
    public List<Model> findMany(Document query, Document sort, int skip, int limit) {
        return collection.find(query).sort(sort).skip(skip).limit(limit).into(new ArrayList<>());
    }

    @Override
    public List<Model> findMany(Document query, Document sort, Document projection, int skip, int limit) {
        return collection.find(query).sort(sort).projection(projection).skip(skip).limit(limit).into(new ArrayList<>());
    }

    @Override
    public List<Model> findAll(Document query, Document sort, Document projection) {
        return collection.find(query).sort(sort).into(new ArrayList<>());
    }

    @Override
    public Boolean updateOne(Document query, Document data) {
        return collection.updateOne(query, data).getModifiedCount() == 1;
    }

    @Override
    public Boolean updateMany(Document query, Document data) {
        return collection.updateMany(query, data).getModifiedCount() > 0;
    }

}
