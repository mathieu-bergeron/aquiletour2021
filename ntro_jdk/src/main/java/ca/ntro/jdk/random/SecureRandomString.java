package ca.ntro.jdk.random;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;

// from: https://neilmadden.blog/2018/08/30/moving-away-from-uuids/ 
public class SecureRandomString {
	private static final SecureRandom random = new SecureRandom();

	public static String generate(int length) {
		byte[] buffer = new byte[length];
		random.nextBytes(buffer);
		return Hex.encodeHexString(buffer);
	}

	public static String generateLoginCode() {
		String code = "";
		for(int i = 0; i < 3; i++) {
			code += random.nextInt(10);
		}
		code += " ";
		for(int i = 0; i < 3; i++) {
			code += random.nextInt(10);
		}
		return code;
	}
}
