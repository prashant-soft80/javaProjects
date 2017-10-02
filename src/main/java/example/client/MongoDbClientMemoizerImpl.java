package example.client;

import core.config.types.BooleanConfig;
import core.config.types.StringConfig;
import example.util.ModuleProperty;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author prash
 * @since 2017-Oct-02
 */
@Singleton
public class MongoDbClientMemoizerImpl implements MongoDbClientMemoizer{
    private final StringConfig clientName;
    private final BooleanConfig enabled;
    private final StringConfig authDatabase;
    private final StringConfig hostNodes;
    private final StringConfig userName;
    private final StringConfig password;

    private final MongoClientFactory mongoClientFactory;
    private final AtomicReference<MongoClientInstance> mongoClient;
    private final MongoClientConfiguration mongoClientConfig;


    @Inject
    public MongoDbClientMemoizerImpl(
            @ModuleProperty("mongodb.clientName") StringConfig clientName,
            @ModuleProperty("mongodb.enabled") BooleanConfig enabled,
            @ModuleProperty("mongodb.authDatabase") StringConfig authDatabase,
            @ModuleProperty("mongodb.hostNodes") StringConfig hostNodes,
            @ModuleProperty("mongodb.userName") StringConfig userName,
            @ModuleProperty("mongodb.password") StringConfig password,
            MongoClientFactory mongoClientFactory) {

        this.clientName = clientName;
        this.enabled = enabled;
        this.authDatabase = authDatabase;
        this.hostNodes = hostNodes;
        this.userName = userName;
        this.password = password;

        this.mongoClientFactory = mongoClientFactory;
        this.mongoClient = new AtomicReference<>();
        this.mongoClientConfig = createMongoClientConfig();

        enabled.addListener(this::closeClientInstance);
    }


    private MongoClientConfiguration createMongoClientConfig() {
        return new MongoClientConfiguration.Builder()
                .name(clientName.get())
                .authenticationDatabaseName(authDatabase.get())
                .hostNodes(hostNodes.get())
                .user(userName.get())
                .password(password.get())
                .build();
    }


    private void closeClientInstance() {
        synchronized (mongoClient) {
            if (mongoClient.get() != null) {
                try {
                    mongoClient.get().close();
                } catch (Throwable ignored) {
                }

                mongoClient.set(null);
            }
        }
    }


    @Override
    public MongoClientInstance get() {
        synchronized (mongoClient) {
            if (!enabled.get()) {
                throw new IllegalStateException("MongoDB is not enabled.");
            }

            if (mongoClient.get() == null) {
                mongoClient.set(mongoClientFactory.create(mongoClientConfig));
            }

            return mongoClient.get();
        }
    }
}
