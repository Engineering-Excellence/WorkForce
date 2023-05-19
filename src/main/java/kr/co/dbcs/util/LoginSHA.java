package kr.co.dbcs.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LoginSHA {
    public static String Salt() throws NoSuchAlgorithmException {
        String salt = "";
        byte[] bytes = {3, 4, 5, 6, 7, 8, 1, 3, 4, 9, 0, 4, 5, 7, 6, 2};

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
