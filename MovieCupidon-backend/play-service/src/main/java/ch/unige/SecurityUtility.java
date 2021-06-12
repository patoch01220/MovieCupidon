package ch.unige;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.microprofile.config.ConfigProvider;

import java.security.spec.KeySpec;
import java.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SecurityUtility {

    private SecurityUtility(){
        // Added a private constructor to hide the implicit public one
    }

    private static final String SECRET_KEY = ConfigProvider.getConfig().getValue("security.aes.key", String.class);
    private static final String SALT = ConfigProvider.getConfig().getValue("security.aes.salt", String.class);
    private static final byte[] iv = ConfigProvider.getConfig().getValue("security.aes.iv", byte[].class);

    private static String getSHA256(String input) {
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    private static String cutString(String stringToCut) throws Exception {
        String[] splitted = stringToCut.split("}");
        splitted[0] += "}";
        if (splitted[1].equals(getSHA256(splitted[0]))) {
            return splitted[0];
        } else {
            throw new Exception("Message and SHA Different");
        }
    }

    public static String decrypt(String strToDecrypt) throws Exception {
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

        String decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

        decrypted = cutString(decrypted);

        return decrypted;
    }

}
