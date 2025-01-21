package com.ytrsoft.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public final class Coded {

    private static final Logger logger = LoggerFactory.getLogger(Coded.class);

    private Coded() {
        throw new UnsupportedOperationException();
    }

    private static IvParameterSpec createIvParameterSpec(byte[] iv) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] ivHash = sha1.digest(iv);
            return new IvParameterSpec(Arrays.copyOfRange(ivHash, 0, 16));
        } catch (NoSuchAlgorithmException e) {
            logger.error("创建IV参数时发生错误: ", e);
            return new IvParameterSpec(new byte[16]);
        }
    }

    private static SecretKeySpec createSecretKeySpec(byte[] key) {
        return new SecretKeySpec(Arrays.copyOfRange(key, 0, 16), "AES");
    }

    public static byte[] sign(byte[] data, byte[] key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(data);
            md.update(key, 0, 8);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("签名时发生错误: ", e);
            return new byte[20];
        }
    }

    public static byte[] encode(byte[] data, byte[] key) {
        try {
            byte[] header = new byte[]{2, 3};
            byte[] iv = new byte[4];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);

            IvParameterSpec ivSpec = createIvParameterSpec(iv);
            SecretKeySpec secretKey = createSecretKeySpec(key);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encrypted = cipher.doFinal(data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(header);
            outputStream.write(iv);
            outputStream.write(encrypted);

            return outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("编码时发生错误: ", e);
            return new byte[data.length + 23];
        }
    }

    public static byte[] decode(byte[] data, byte[] key) {
        try {
            byte[] iv = Arrays.copyOfRange(data, 2, 6);
            IvParameterSpec ivSpec = createIvParameterSpec(iv);
            SecretKeySpec secretKey = createSecretKeySpec(key);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] encrypted = Arrays.copyOfRange(data, 7, data.length);
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            logger.error("解码时发生错误: ", e);
            return new byte[data.length - 7];
        }
    }
}
