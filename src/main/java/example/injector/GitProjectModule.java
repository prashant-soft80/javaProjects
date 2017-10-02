package example.injector;

import com.google.inject.AbstractModule;
import example.client.CustomMongoClient;
import example.services.MessageService;
import example.services.MongoDBService;

public class GitProjectModule extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(CustomMongoClient.class).asEagerSingleton();
        bind(MongoDBService.class).asEagerSingleton();
        bind(MessageService.class).asEagerSingleton();
    }
}
