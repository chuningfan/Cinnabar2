package cinnabar.core.util;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;

public class AuthenticationServiceURICollection {
	
	private static final Set<String> URI_SET = Sets.newHashSet();
	
	public static void set(String uri) {
		URI_SET.add(uri);
	}
	
	public static boolean isAuthenticationURI(String uri) {
		return URI_SET.contains(uri);
	}
	
	public static void setAll(String...uris) {
		URI_SET.addAll(Arrays.asList(uris));
	}
	
}
