package me.mprey.database.interf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD);

/**
 * Created by Mason Prey on 2/8/16.
 */
public @interface DatabaseGetField {

    String name();
    String databaseType();
    String defaultValue() default "";
    boolean notNull() default true;
    boolean autoIncrement() default false;

}
