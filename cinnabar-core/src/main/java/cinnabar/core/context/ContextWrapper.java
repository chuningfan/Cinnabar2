package cinnabar.core.context;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

public class ContextWrapper {

	public static Cookie getCookie(Context context, final String contextName, final String domain) throws InvalidKeyException, 
	IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		String cookieString = context.toCookieString();
		Cookie c = new Cookie(contextName, encryptVal(cookieString));
		c.setDomain(domain);
		c.setHttpOnly(false);
		return c;
	}
	
	public static Context wrap(Cookie cookie) {
		Context context = new Context();
		return context;
	}
	
	private static String encryptVal(String dtoVal) throws IllegalBlockSizeException, BadPaddingException, 
	NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
		KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("aes");
        SecretKey aesSecretKey = aesKeyGenerator.generateKey();
        Cipher aesCipher = Cipher.getInstance("aes");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
        byte[] aseResultBytes = aesCipher.doFinal(dtoVal.getBytes());
        return new String(aseResultBytes, "UTF-8");
	}
	
	public static Context decryptCookie(String cookieVal) throws IllegalBlockSizeException, BadPaddingException, 
	InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("aes");
        SecretKey aesSecretKey = aesKeyGenerator.generateKey();
		Cipher aesCipher = Cipher.getInstance("aes");
		aesCipher.init(Cipher.DECRYPT_MODE, aesSecretKey);
		byte[] aseResultBytes = aesCipher.doFinal(cookieVal.getBytes());
		String result = new String(aseResultBytes, "UTF-8");
		Context context = new Context();
		/*
		 * "userId=" + userId + ";userRole=" + userRole + ";loggedTime=" + 
		loggedTime + ";ipAddress=" + ipAddress + ";redisId=" + redisId + ";rememberMe=" + rememberMe;
		 */
		if (StringUtils.isBlank(result)) {
			return null;
		}
		String[] keyValArray = result.split(";");
		context.setUserId(Long.valueOf(keyValArray[0].split("=")[1]));
		context.setUserRole(keyValArray[1].split("=")[1]);
		context.setLoggedTime(keyValArray[2].split("=")[1]);
		context.setIpAddress(keyValArray[3].split("=")[1]);
		context.setRedisId(keyValArray[4].split("=")[1]);
		context.setRememberMe(keyValArray[5].split("=")[1]);
		return context;
	}
	
}
