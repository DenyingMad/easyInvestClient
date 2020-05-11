package com.cgpanda.easyinvest;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Security {

    public static String generateHash(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = getSalt();
        PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray(), salt, 32768, 64 * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        return toHex(salt) + ":" + toHex(hash);
    }

    public static boolean validatePassword(String storedString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedString.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] storedHash = fromHex(parts[1]);
        String password = parts[2];

        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 32768, storedHash.length * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] newHash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

        int diff = storedHash.length ^ newHash.length;
        for (int i =0; i < storedHash.length && i < newHash.length; i++){
            diff |= storedHash[i] ^ newHash[i];
        }

        return diff == 0;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array){
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0){
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] fromHex(String hex){
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
