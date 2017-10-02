package core.config.types;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public interface ConfigChangeValidator<T> {
    void validate(T t);
}
