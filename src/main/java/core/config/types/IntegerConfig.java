package core.config.types;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class IntegerConfig extends Config<Integer> {
    private final AtomicInteger intValue = new AtomicInteger();

    public IntegerConfig(String value) {
        set(value);
    }

    public IntegerConfig(int value) {
        set(value);
    }

    @Override
    public void set(String value) {
        noValueProvidedRef.set(value.isEmpty());
        if (noValueProvidedRef.get()) {
            set(0);
        } else {
            set(Integer.valueOf(value));
        }
    }

    public void set(int intValue) {
        notifyValidators(intValue);
        this.intValue.set(intValue);
        notifyListeners();
    }

    public int get() {
        return intValue.get();
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}
