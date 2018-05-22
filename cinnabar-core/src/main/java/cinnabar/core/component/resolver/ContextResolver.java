package cinnabar.core.component.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cinnabar.core.annotation.CinnabarContext;
import cinnabar.core.component.pool.ContextPool;

/**
 * Resolve implicit parameter 'context' to inject into APIs
 * 
 * @author Vic.Chu
 *
 */
public class ContextResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CinnabarContext.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return ContextPool.get();
	}

}
