package example.util;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface RunnableWithThrowable<O, T extends Throwable> {

    void run() throws Throwable;

}
