package example.client;

import com.mongodb.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface MongoClientInstance {

    /**
     * Get a list of the database names
     * throws N6MongoException
     */
    MongoIterable<String> listDatabaseNames();

    /**
     * Gets the list of databases
     * throws N6MongoException
     */
    ListDatabasesIterable<Document> listDatabases();

    /**
     * @param databaseName the name of the database to retrieve
     * @return a {@code MongoDatabase} representing the specified database
     */
    MongoDatabase getDatabase(String databaseName);

    /**
     * With retry mechanism in place.
     *
     * @param databaseName
     * @param retries
     * @param interval
     * @return
     */
    MongoDatabase getDatabase(String databaseName, int retries, int interval);

    /**
     * Return the mongoclient underlying
     *
     * @return a {@code {@link MongoClient}}
     */
    MongoClient getUnderlyingMongoClient();


    String getName();

    String getConnectString();

    String getUsername();

    void dropDatabase(final String dbName);

    void close();

}
