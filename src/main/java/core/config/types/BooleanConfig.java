package core.config.types;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class BooleanConfig extends Config<Boolean> {
    private final AtomicBoolean booleanValue = new AtomicBoolean();

    public BooleanConfig(String value) {
        set(value);
    }

    public BooleanConfig(boolean value) {
        set(value);
    }

    @Override
    public void set(String value) {
        noValueProvidedRef.set(value.isEmpty());
        if (noValueProvidedRef.get()) {
            set(false);
        } else {
            set(Boolean.valueOf(value));
        }
    }

    public void set(boolean booleanValue) {
        notifyValidators(booleanValue);
        this.booleanValue.set(booleanValue);
        notifyListeners();
    }

    public boolean get() {
        return booleanValue.get();
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}
