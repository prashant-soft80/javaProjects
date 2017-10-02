package example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import example.injector.GitProjectModule;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public abstract class LoadModule {

    private Injector injector;

    public void init() {
        injector = Guice.createInjector(new GitProjectModule());
    }

    public Injector getInjector() {
        return injector;
    }
}
