package core.config.types;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface ConfigStateProvider {
    ConfigState getConfigState();

    String getStorage();
}
