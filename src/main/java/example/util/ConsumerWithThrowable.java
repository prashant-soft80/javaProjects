package example.util;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface ConsumerWithThrowable<O, T extends Throwable> {

    void accept(O o) throws T;

}
