package example.util;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author prash
 * @since 2017-Oct-02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD.FIELD, ElementType.PARAMETER})
@BindingAnnotation
public @interface ModuleProperty {

    String value();
}

