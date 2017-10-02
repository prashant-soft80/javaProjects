package example.util;

import java.util.function.Supplier;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class AssertionUtils {
    // Assertions
    public static <T> T notNull(T object, String name) {
        if (object == null) {
            throw new NullPointerException("Field " + name + " cannot be null.");
        }
        return object;
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion failed:" + message);
        }
    }


    public static <T extends Throwable> void assertTrue(boolean condition, Supplier<? extends T> exceptionSupplier) throws T {
        if (!condition) {
            throw exceptionSupplier.get();
        }
    }

    public static <T extends Throwable> void assertFalse(boolean condition, Supplier<? extends T> exceptionSupplier) throws T {
        assertTrue(!condition, exceptionSupplier);
    }
}
