package cinnabar.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * encrypt/decrypt->context/cookie
 * 
 * @author Vic.Chu
 *
 */
public class AESEncryptor {
	
	private static Logger logger = LoggerFactory.getLogger(AESEncryptor.class);
	private static final AESEncryptor instance = new AESEncryptor();
	private final SecretKeySpec secretKeySpec;

	private static final String AES_128_KEY = "-74,-58,-97,-78,-10,12,35,12,-87,45,-2,27,-103,-23,-40,-20";
	
	private AESEncryptor() {
		String[] keys = AES_128_KEY.split(",");
		if (null == keys || keys.length == 0) {
			logger.error(
					"AES encryption key not found in properties file. There should be a \'aes-128-key\' property.");
		}

		byte[] bytes = new byte[keys.length];

		for (int e = 0; e < keys.length; ++e) {
			bytes[e] = Byte.parseByte(keys[e]);
		}

		try {
			this.secretKeySpec = new SecretKeySpec(bytes, "AES");
		} catch (Exception arg3) {
			logger.error("Unable to create AES encryption key", arg3);
			throw new IllegalArgumentException(arg3);
		}
	}

	public static AESEncryptor getInstance() {
		return instance;
	}

	public String encryptToHex(String message) throws Exception {
		try {
			Cipher e = Cipher.getInstance("AES");
			e.init(1, this.secretKeySpec);
			byte[] encrypted = e.doFinal(message.getBytes());
			return Hex.encodeHexString(encrypted);
		} catch (Exception arg3) {
			throw new Exception("Unable to encrypt message", arg3);
		}
	}

	public String decryptFromHex(String message) throws Exception {
		try {
			byte[] e = Hex.decodeHex(message.toCharArray());
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, this.secretKeySpec);
			byte[] decrypted = cipher.doFinal(e);
			return new String(decrypted);
		} catch (Exception arg4) {
			throw new Exception("Unable to decrypt message", arg4);
		}
	}
	
}
