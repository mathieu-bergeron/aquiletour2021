package ca.ntro.jdk.random;

import java.security.SecureRandom;
import java.util.Base64;

// from: https://neilmadden.blog/2018/08/30/moving-away-from-uuids/ 
public class SecureRandomString {
	private static final SecureRandom random = new SecureRandom();
	private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

	public static String generate() {
		byte[] buffer = new byte[20];
		random.nextBytes(buffer);
		return encoder.encodeToString(buffer);
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
