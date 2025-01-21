package com.ytrsoft.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;

public final class Coded {

    private static final Logger logger = LoggerFactory.getLogger(Coded.class);

    private static final byte[] HEAD = new byte[] {2, 3};
    private static final byte[] NOP = new byte[] {0};
    private static final int IV_LENGTH = 4;
    private static final int AES_KEY_LENGTH = 16;
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SHA1_ALGORITHM = "SHA-1";

    private Coded() {
        throw new UnsupportedOperationException();
    }

    public static byte[] sign(byte[] data, byte[] key) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance(SHA1_ALGORITHM);
            sha1.update(data);
            sha1.update(key, 0, 8);
            return sha1.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("签名失败: {}", e.getMessage());
            return new byte[28];
        }
    }

    private static byte[] hashIV(byte[] iv) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance(SHA1_ALGORITHM);
        return sha1.digest(iv);
    }

    private static byte[] generateRandomIV() throws NoSuchAlgorithmException {
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return hashIV(iv);
    }

    private static Cipher buildCipher(int mode, byte[] iv, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        iv = Arrays.copyOfRange(iv, 0, AES_KEY_LENGTH);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        key = Arrays.copyOfRange(key, 0, AES_KEY_LENGTH);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(mode, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    public static byte[] encode(byte[] data, byte[] key) {
        try {
            byte[] iv = generateRandomIV();
            Cipher cipher = buildCipher(Cipher.ENCRYPT_MODE, iv, key);
            byte[] encrypted = cipher.doFinal(data);
            ByteStream bs = new ByteStream();
            bs.put(HEAD);
            bs.put(iv);
            bs.put(NOP);
            bs.put(encrypted);
            byte[] result = bs.get();
            logger.info(Arrays.toString(result));
            return result;
        } catch (Exception e) {
            logger.error("加密失败: {}", e.getMessage());
            return new byte[data.length + 23];
        }
    }


    public static byte[] decode(byte[] data, byte[] key) {
        try {
            byte[] iv = hashIV(Arrays.copyOfRange(data, 2, 6));
            Cipher cipher = buildCipher(Cipher.DECRYPT_MODE, iv, key);
            byte[] decrypted = Arrays.copyOfRange(data, 7, data.length);
            return cipher.doFinal(decrypted);
        } catch (Exception e) {
            logger.error("解密失败: {}", e.getMessage());
            return new byte[data.length - 7];
        }
    }
}