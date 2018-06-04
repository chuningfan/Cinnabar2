package cinnabar.core.context;

import javax.servlet.http.Cookie;

import com.fasterxml.jackson.databind.ObjectMapper;

import cinnabar.core.util.AESEncryptor;

/**
 * cookie and context wrapper
 * 
 * @author Vic.Chu
 *
 */
public class CookieContextWrapper {

	public static Context getContextFromCookie(Cookie contextCookie) {
		String cookieString = contextCookie.getValue();
		AESEncryptor aes = AESEncryptor.getInstance();
		try {
			ObjectMapper mapper = new ObjectMapper(); 
			return mapper.readValue(aes.decryptFromHex(cookieString), Context.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encryptContextJson(String json) {
		AESEncryptor aes = AESEncryptor.getInstance();
		try {
			return aes.encryptToHex(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
