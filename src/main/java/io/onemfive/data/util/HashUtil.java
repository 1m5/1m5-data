package io.onemfive.data.util;

import io.onemfive.data.DID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Hashtable;

public class HashUtil {

    public static String generateShortHash(byte[] contentToHash) throws NoSuchAlgorithmException {
        return generateShortHash(getSalt(), contentToHash);
    }

    public static String generateShortHash(String contentToHash) throws NoSuchAlgorithmException {
        return generateShortHash(getSalt(), contentToHash.getBytes());
    }

    private static String generateShortHash(byte[] salt, byte[] contentToHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(salt);
        byte[] bytes = md.digest(contentToHash);
        String hashString = toHex(salt)+":"+toHex(bytes);
        return Base64.encode(hashString);
    }

    public static Boolean verifyShortHash(byte[] contentToVerify, String hashToVerify) throws NoSuchAlgorithmException {
        String hashString = Base64.decodeToString(hashToVerify);
        String[] parts = hashString.split(":");
        byte[] bytes = fromHex(parts[0]);
        String contentHash = generateShortHash(bytes, contentToVerify);
        return hashToVerify.equals(contentHash);
    }

    public static String generateHash(String contentToHash) throws NoSuchAlgorithmException {
        int iterations = 1000;
        byte[] salt = getSalt();
        byte[] hash;
        try {
            PBEKeySpec spec = new PBEKeySpec(contentToHash.toCharArray(), salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String hashString = iterations + ":" + toHex(salt) + ":" + toHex(hash);
        return Base64.encode(hashString);
    }

    public static Boolean verifyHash(String contentToVerify, String hashToVerify) {
        String hashString = Base64.decodeToString(hashToVerify);
        String[] parts = hashString.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(contentToVerify.toCharArray(), salt, iterations, hash.length * 8);
        byte[] testHash;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) {
        DID did = new DID();
        did.setAlias("Alice");
        try {
            String hash = HashUtil.generateHash(did.getAlias());
            System.out.println("Alias Full Hash: "+hash);
            Boolean aliasVerified = HashUtil.verifyHash("Alice", hash);
            System.out.println("Alias Full Hash Verified: "+aliasVerified);
            String shortHash = HashUtil.generateShortHash(did.getAlias());
            System.out.println("Alias Short Hash: "+shortHash);
            Boolean shortHashVerified = HashUtil.verifyShortHash(did.getAlias().getBytes(), shortHash);
            System.out.println("Alias Short Hash Verified: "+shortHashVerified);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
