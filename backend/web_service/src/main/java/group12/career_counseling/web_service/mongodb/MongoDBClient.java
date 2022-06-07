package group12.career_counseling.web_service.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import eu.dozd.mongo.MongoMapper;
import group12.career_counseling.web_service.utils.enumeration.ApplicationProperties;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MongoDBClient {
    private final MongoClient mongoClient;


    @Autowired
    public MongoDBClient(Environment environment) {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(MongoMapper.getProviders()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString(environment.getProperty(ApplicationProperties.mongodb, "localhost:27017")))
                .build();
        mongoClient = MongoClients.create(settings);
    }

    public MongoClient getClient() {
        return mongoClient;
    }
}
