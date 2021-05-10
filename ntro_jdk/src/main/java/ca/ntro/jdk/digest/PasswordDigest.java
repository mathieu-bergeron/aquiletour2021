package ca.ntro.jdk.digest;

import org.apache.commons.codec.digest.DigestUtils;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.random.SecureRandomString;

public class PasswordDigest {
	
	public static String salt = SecureRandomString.generate();
	
	public static void initialize(String salt) {
		T.call(PasswordDigest.class);
		
		PasswordDigest.salt = salt;
	}
	
	public static String passwordDigest(String password) {
		T.call(PasswordDigest.class);

		return DigestUtils.sha1Hex(salt + ":" +  password);
	}

}
