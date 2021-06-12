package ch.unige;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.resource.spi.ConfigProperty;

import org.eclipse.microprofile.config.ConfigProvider;

import java.nio.charset.StandardCharsets;
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


    public static String encrypt(String strToEncrypt) {
        try {

            strToEncrypt += getSHA256(strToEncrypt); //concatenate message + sha256(message)

            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }


}
