package cinnabar.core.context;

import javax.servlet.http.Cookie;

import cinnabar.core.util.AESEncryptor;

/**
 * cookie and context wrapper
 * 
 * @author Vic.Chu
 *
 */
public class CookieContextWrapper {

	/**
	 * get cookie by context
	 * @param context
	 * @param contextName
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	public static Cookie getCookie(Context context, final String contextName, final String domain, String path) throws Exception {
		String[] array = new String[]{
				context.getUserId() + "",
				context.getRedisId(),
				context.getRememberMe(),
				context.getLoggedTime(),
				context.getClientType(),
				context.getUserRole(),
				context.getIpAddress()
		};
		Cookie c = new Cookie(contextName, encryptVal(array));
		c.setDomain(domain);
		c.setHttpOnly(false);
		c.setPath(path);
		return c;
	}
	
	/**
	 * get context from cookie
	 * @param cookie
	 * @return
	 */
	public static Context wrap(Cookie cookie) {
		Context context = new Context();
		return context;
	}
	
	private static String encryptVal(String[] array) throws Exception {
		StringBuilder value = new StringBuilder();
		for (int e = 0; e < 9; ++e) {
			value.append(array[e]);
			if (e < 8) {
				value.append(",");
			}
		}
		String cookieString = AESEncryptor.getInstance().encryptToHex(value.toString());
		return cookieString;
	}
	
	public static Context decryptCookie(String cookieVal) {
		String decoded;
		try {
			decoded = AESEncryptor.getInstance().decryptFromHex(cookieVal);
		} catch (Exception arg4) {
			throw new IllegalArgumentException("Unable to decrypt value", arg4);
		}
		String[] values = decoded.split(",");
		Context context = new Context();
		context.setUserId(Long.valueOf(values[0]));
		context.setRedisId(values[1]);
		context.setRememberMe(values[2]);
		context.setLoggedTime(values[3]);
		context.setClientType(values[4]);
		context.setUserRole(values[5]);
		context.setIpAddress(values[6]);
		return context;
	}
	
}
