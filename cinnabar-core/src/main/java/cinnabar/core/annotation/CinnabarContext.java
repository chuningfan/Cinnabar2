package cinnabar.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For getting user context, define a custom annotation for context, to all API it's a implicit parameter
 * @author Vic.Chu
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CinnabarContext {

}
