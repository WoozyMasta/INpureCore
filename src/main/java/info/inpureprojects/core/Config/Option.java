package info.inpureprojects.core.Config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by den on 10/31/2014.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

    String category();

    String key();

    String comment() default "";

    boolean value() default true;

}
