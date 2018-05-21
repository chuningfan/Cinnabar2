package cinnabar.core.util;

import java.util.Set;

import com.google.common.collect.Sets;

public class ServiceUtils {
	
	private static final Set<String> CONTEXT_URI = Sets.newConcurrentHashSet();
	
	public static void setServices(Set<String> services) {
		CONTEXT_URI.addAll(services);
	}
	
	public static Set<String> getContextServices() {
		return CONTEXT_URI;
	}
	
}
