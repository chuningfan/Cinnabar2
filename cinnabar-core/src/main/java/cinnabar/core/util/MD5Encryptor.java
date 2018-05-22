package cinnabar.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.buf.HexUtils;

public class MD5Encryptor {
	
	public static String getEncryptedString(String unencryptedString) throws NoSuchAlgorithmException {
		String md5str = "";
		unencryptedString += "cinnabar is cool";
		MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] input = unencryptedString.getBytes();
        byte[] buff = md.digest(input);
        md5str = HexUtils.toHexString(buff);    
        return md5str;
	}
	
}
