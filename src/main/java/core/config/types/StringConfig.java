package core.config.types;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class StringConfig extends Config<String> {

    private final AtomicReference<String> stringValue = new AtomicReference<>();

    public StringConfig(String value) {
        set(value);
    }

    public void set(String string) {
        notifyValidators(string);
        noValueProvidedRef.set(string == null || string.isEmpty());
        this.stringValue.set(string);
        notifyListeners();
    }

    public String get() {
        return stringValue.get();
    }

    @Override
    public String toString() {
        return get();
    }
}
