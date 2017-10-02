package example.client;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomMongoClient extends MongoClient {
    /**
     * Creates an instance based on a (single) mongodb node (localhost, default port).
     */
    private final MongoClient mongoClient;

    @Inject
    public CustomMongoClient() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @Override
    public MongoDatabase getDatabase(final String dbName) {
        return mongoClient.getDatabase(dbName);
    }
}
