package example.services;

import com.google.inject.ImplementedBy;
import com.mongodb.client.MongoCollection;
import example.services.db.MongoDBServiceImpl;

@ImplementedBy(MongoDBServiceImpl.class)
public interface MongoDBService {
    MongoCollection getCollection(String collectionName);
}
