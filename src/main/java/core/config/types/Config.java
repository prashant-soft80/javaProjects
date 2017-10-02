package core.config.types;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public abstract class Config<T> {

    private final List<ConfigChangedListener> listeners = new CopyOnWriteArrayList<>();
    private final List<ConfigChangeValidator<T>> validators = new CopyOnWriteArrayList<>();
    private ConfigStateProvider configStateProvider;
    protected final AtomicBoolean noValueProvidedRef = new AtomicBoolean();

    public void addListener(ConfigChangedListener listener) {
        listeners.add(listener);
    }

    public void addValidator(ConfigChangeValidator<T> validator) {validators.add(validator); }

    /**
     * Note! You need to call this method to free resources if you are adding listener in non singleton class.
     * @param listener - the exact instance added by addListener method.
     */
    public void removeListener(ConfigChangedListener listener) {
        listeners.remove(listener);
    }

    public void removeValidator(ConfigChangeValidator<T> validator) {
        validators.remove(validator);
    }

    protected void notifyListeners() {
        listeners.forEach(ConfigChangedListener::configChanged);
    }

    protected void notifyValidators(T t) {
        validators.forEach(validator -> validator.validate(t));
    }

    public abstract void set(String value);

    public String getStorage() {
        return configStateProvider == null ? null : configStateProvider.getStorage();
    }

    public void setConfigStateProvider(ConfigStateProvider configStateProvider) {
        this.configStateProvider = configStateProvider;
    }

    public ConfigState getConfigState() {
        return configStateProvider == null ? ConfigState.VOLATILE : configStateProvider.getConfigState();
    }

    public boolean noValueProvided() {
        return noValueProvidedRef.get();
    }
}
