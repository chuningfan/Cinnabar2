package cinnabar.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For getting user context, define a custom annotation for context, to all API it's a implicit parameter
 * front-end does not need to input this parameter, this will be added to request automatic
 * @author Vic.Chu
 *
 *	e.g
 *  @RequestMapping("/test")
 *  public String test(@RequestParam String str, @CinnabarContext Context context)
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CinnabarContext {

}
