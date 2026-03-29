package com.ytrsoft.momo.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cryptographic utilities: AES-CBC encryption/decryption, SHA-1 signing, MD5 hashing,
 * and Base64 encoding/decoding.
 *
 * <p>Uses a custom binary wire format: {@code [0x02, 0x03] + [4-byte IV] + [0x00] + [ciphertext]}.
 */
public final class Crypto {

  private static final Logger LOG = LoggerFactory.getLogger(Crypto.class);

  private static final byte[] HEAD = {2, 3};
  private static final byte[] NOP = {0};
  private static final int IV_LENGTH = 4;
  private static final int AES_KEY_LENGTH = 16;
  private static final int SIGN_KEY_PREFIX_LENGTH = 8;
  private static final int SHA1_DIGEST_LENGTH = 20;
  private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
  private static final String AES_ALGORITHM = "AES";

  private Crypto() {}

  // ---------------------------------------------------------------------------
  // Hashing
  // ---------------------------------------------------------------------------

  /**
   * Returns the lowercase hex MD5 digest of the input string.
   *
   * @param input the string to hash
   * @return the 32-character hex digest, or the original string if MD5 is unavailable
   */
  public static String md5(String input) {
    try {
      byte[] digest = MessageDigest.getInstance("MD5")
          .digest(input.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(digest);
    } catch (NoSuchAlgorithmException e) {
      LOG.error("MD5 unavailable", e);
      return input;
    }
  }

  // ---------------------------------------------------------------------------
  // Signing
  // ---------------------------------------------------------------------------

  /**
   * Computes a SHA-1 HMAC-like signature: {@code SHA1(data + key[0..7])}.
   *
   * @param data the data to sign
   * @param key the signing key (first 8 bytes are used)
   * @return the 20-byte SHA-1 digest, or a zero-filled array on failure
   */
  public static byte[] sign(byte[] data, byte[] key) {
    try {
      MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
      sha1.update(data);
      sha1.update(key, 0, SIGN_KEY_PREFIX_LENGTH);
      return sha1.digest();
    } catch (NoSuchAlgorithmException e) {
      LOG.error("SHA-1 unavailable", e);
      return new byte[SHA1_DIGEST_LENGTH];
    }
  }

  // ---------------------------------------------------------------------------
  // AES-CBC Encryption / Decryption
  // ---------------------------------------------------------------------------

  /**
   * Encrypts data using AES-CBC with a random 4-byte IV.
   *
   * @param data the plaintext
   * @param key the encryption key
   * @return the encoded payload in the custom wire format
   */
  public static byte[] encode(byte[] data, byte[] key) {
    try {
      byte[] iv = randomIv();
      byte[] encrypted = doCipher(Cipher.ENCRYPT_MODE, iv, key, data);
      return concatArrays(HEAD, iv, NOP, encrypted);
    } catch (Exception e) {
      LOG.error("Encryption failed", e);
      return new byte[0];
    }
  }

  /**
   * Decrypts data produced by {@link #encode(byte[], byte[])}.
   *
   * @param data the encoded payload
   * @param key the decryption key
   * @return the decrypted plaintext
   */
  public static byte[] decode(byte[] data, byte[] key) {
    try {
      byte[] iv = Arrays.copyOfRange(data, 2, 6);
      byte[] ciphertext = Arrays.copyOfRange(data, 7, data.length);
      return doCipher(Cipher.DECRYPT_MODE, iv, key, ciphertext);
    } catch (Exception e) {
      LOG.error("Decryption failed", e);
      return new byte[0];
    }
  }

  // ---------------------------------------------------------------------------
  // Base64
  // ---------------------------------------------------------------------------

  /** Encodes a byte array to a Base64 string. */
  public static String base64Encode(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  /** Decodes a Base64-encoded string to a byte array. */
  public static byte[] base64Decode(String encoded) {
    return Base64.getDecoder().decode(encoded);
  }

  // ---------------------------------------------------------------------------
  // Byte utilities
  // ---------------------------------------------------------------------------

  /** Concatenates multiple byte arrays into a single array. */
  public static byte[] concatArrays(byte[]... arrays) {
    int totalLength = 0;
    for (byte[] a : arrays) {
      totalLength += a.length;
    }
    byte[] result = new byte[totalLength];
    int offset = 0;
    for (byte[] a : arrays) {
      System.arraycopy(a, 0, result, offset, a.length);
      offset += a.length;
    }
    return result;
  }

  // ---------------------------------------------------------------------------
  // Internal helpers
  // ---------------------------------------------------------------------------

  private static byte[] doCipher(int mode, byte[] iv, byte[] key, byte[] input) throws Exception {
    MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
    byte[] hashedIv = Arrays.copyOf(sha1.digest(iv), AES_KEY_LENGTH);
    byte[] trimmedKey = Arrays.copyOf(key, AES_KEY_LENGTH);
    Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
    cipher.init(mode, new SecretKeySpec(trimmedKey, AES_ALGORITHM), new IvParameterSpec(hashedIv));
    return cipher.doFinal(input);
  }

  private static byte[] randomIv() {
    byte[] iv = new byte[IV_LENGTH];
    new SecureRandom().nextBytes(iv);
    return iv;
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
