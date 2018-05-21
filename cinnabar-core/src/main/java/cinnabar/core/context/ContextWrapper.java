package cinnabar.core.context;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContextWrapper {

	public static Cookie getCookie(Context context, final String contextName, final String domain) throws InvalidKeyException, 
	IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, JsonProcessingException {
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
	InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, JsonParseException, JsonMappingException, IOException {
		KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("aes");
        SecretKey aesSecretKey = aesKeyGenerator.generateKey();
		Cipher aesCipher = Cipher.getInstance("aes");
		aesCipher.init(Cipher.DECRYPT_MODE, aesSecretKey);
		byte[] aseResultBytes = aesCipher.doFinal(cookieVal.getBytes());
		String result = new String(aseResultBytes, "UTF-8");
		if (StringUtils.isBlank(result)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		Context context = mapper.readValue(result, Context.class);
		return context;
	}
	
}
