package example.client;

import java.util.Set;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface MongoClientFactory {
    MongoClientInstance create(MongoClientConfiguration cfg);

    MongoClientInstance getMongoClientInstance(String name);

    Set<String> getMongoClientsName();
}
