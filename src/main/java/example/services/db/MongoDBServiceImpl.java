package example.services.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import example.client.CustomMongoClient;
import example.services.MongoDBService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MongoDBServiceImpl implements MongoDBService {
    private final MongoDatabase mongoDatabase;

    @Inject
    public MongoDBServiceImpl(CustomMongoClient customMongoClient) {
        mongoDatabase = customMongoClient.getMongoClient().getDatabase("example");
    }

    @Override
    public MongoCollection getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }
}
