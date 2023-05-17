package kr.co.dbcs.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class LoginSHA {
	public static String Salt() throws NoSuchAlgorithmException {
		String salt = "";
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		salt = new String(Base64.getEncoder().encode(bytes));
		
		return salt;
	}
	
	public static String SHA512(String pw, String hash) throws NoSuchAlgorithmException {
		String salt = hash + pw;
		String hex = null;
		
		MessageDigest msg = MessageDigest.getInstance("SHA-512");
		msg.update(salt.getBytes());
		
		hex = String.format("%128x", new BigInteger(1, msg.digest()));
		
		return hex;
	}
}
