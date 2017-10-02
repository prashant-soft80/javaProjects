package example;

import com.mongodb.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import example.client.MongoClientInstance;
import example.client.MongoDbClientMemoizer;
import junit.framework.TestCase;
import org.bson.Document;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public abstract class BaseTestCase extends TestCase {

    private static class TestMongoClientInstance implements MongoClientInstance, MongoDbClientMemoizer {
        private final MongoClient mongoClient;

        TestMongoClientInstance(final MongoClient mongoClient) {
            this.mongoClient = mongoClient;
        }

        @Override
        public MongoIterable<String> listDatabaseNames() {
            return this.mongoClient.listDatabaseNames();
        }

        @Override
        public ListDatabasesIterable<Document> listDatabases() {
            return this.mongoClient.listDatabases();
        }

        @Override
        public MongoDatabase getDatabase(final String databaseName) {
            return this.mongoClient.getDatabase(databaseName);
        }

        @Override
        public MongoClient getUnderlyingMongoClient() {
            return this.mongoClient;
        }

        @Override
        public String getName() {
            return "TEST";
        }

        @Override
        public String getConnectString() {
            return this.mongoClient.getConnectPoint();
        }

        @Override
        public String getUsername() {
            return "no-user";
        }


        @Override
        public void dropDatabase(final String dbName) {
            this.mongoClient.dropDatabase(dbName);
        }

        @Override
        public void close() {
            this.mongoClient.close();
        }


        @Override
        public MongoDatabase getDatabase(final String databaseName, final int retries, final int interval) {
            return this.mongoClient.getDatabase(databaseName);
        }

        @Override
        public MongoClientInstance get() {
            return this;
        }
    }
}
