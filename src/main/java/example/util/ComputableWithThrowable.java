package example.util;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface ComputableWithThrowable<T> {
    T compute() throws Throwable;
}
