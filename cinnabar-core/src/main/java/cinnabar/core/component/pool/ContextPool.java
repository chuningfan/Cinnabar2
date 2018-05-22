package cinnabar.core.component.pool;

import cinnabar.core.context.Context;

/**
 * for storing context data (if existing), for current thread when request to server
 * for @ContextResolver to get context data.
 * 
 * @author Vic.Chu
 *
 */
public class ContextPool {
	
	private static final ThreadLocal<Context> CXT_POOL = new ThreadLocal<>();
	
	// set context for current thread
	public static void setContext(Context context) {
		CXT_POOL.set(context);
	}
	
	// get context for current thread
	public static Context get() {
		return CXT_POOL.get();
	}
	
	// remove current thread's context
	public static void destroy() {
		Context cxt = CXT_POOL.get();
		if (cxt != null) {
			CXT_POOL.remove();
		}
	}
	
}
