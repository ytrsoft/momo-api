package com.ytrsoft.momo.core;

import com.ytrsoft.momo.MomoException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performs an ECDH key exchange using the secp192k1 curve against a hardcoded server public key.
 *
 * <p>This class is stateless; each call to {@link #execute()} generates a fresh key pair.
 */
public final class KeyExchange {

  private static final Logger LOG = LoggerFactory.getLogger(KeyExchange.class);

  private static final String EC_CURVE = "secp192k1";
  private static final String BC_PROVIDER = "BC";
  private static final byte[] OFFSET = "Iu0WKHFy".getBytes(StandardCharsets.UTF_8);

  private static final byte[] SERVER_PUBLIC_KEY = {
      4, -59, 23, 69, 30, 95, 105, 75, 121, -35, -97, -23, -51, -76, -108, 121, 34, 87,
      -20, -31, -125, 17, 35, 64, -126, -12, 64, -44, -79, 51, -93, -38, 116, -119, -80,
      82, 88, 5, 2, -13, 70, 77, 28, -1, -115, 28, 101, -85, -8
  };

  static {
    if (Security.getProvider(BC_PROVIDER) == null) {
      Security.addProvider(new BouncyCastleProvider());
    }
  }

  private KeyExchange() {}

  /**
   * Performs a fresh ECDH key exchange and returns the result.
   *
   * @return an immutable {@link ExchangeResult}
   * @throws MomoException if the exchange fails
   */
  public static ExchangeResult execute() {
    try {
      X9ECParameters x9 = ECNamedCurveTable.getByName(EC_CURVE);
      ECDomainParameters domain = new ECDomainParameters(
          x9.getCurve(), x9.getG(), x9.getN(), x9.getH());

      KeyPair keyPair = generateKeyPair();
      ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
      ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

      // Compute ECDH shared secret.
      ECDHBasicAgreement agreement = new ECDHBasicAgreement();
      agreement.init(new ECPrivateKeyParameters(privateKey.getD(), domain));

      ECPoint serverPoint = x9.getCurve().decodePoint(SERVER_PUBLIC_KEY);
      BigInteger sharedSecret = agreement.calculateAgreement(
          new ECPublicKeyParameters(serverPoint, domain));

      byte[] secretBytes = stripLeadingZero(sharedSecret.toByteArray());
      String key = Crypto.base64Encode(secretBytes);

      // Build client key (ck) and key version (kv).
      byte[] encodedPub = publicKey.getQ().getEncoded(false);
      byte[] encryptedPub = Crypto.encode(encodedPub, OFFSET);
      String ck = Crypto.base64Encode(encryptedPub);
      String kv = Crypto.md5(ck).substring(0, 8);

      LOG.info("Key exchange completed, kv={}", kv);
      return new ExchangeResult(key, ck, kv);
    } catch (Exception e) {
      throw new MomoException("ECDH key exchange failed", e);
    }
  }

  private static KeyPair generateKeyPair() throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", BC_PROVIDER);
    kpg.initialize(new ECGenParameterSpec(EC_CURVE));
    return kpg.generateKeyPair();
  }

  private static byte[] stripLeadingZero(byte[] bytes) {
    if (bytes.length > 0 && bytes[0] == 0) {
      return Arrays.copyOfRange(bytes, 1, bytes.length);
    }
    return bytes;
  }

  /**
   * Immutable result of an ECDH key exchange.
   */
  public static final class ExchangeResult {

    private final String key;
    private final String ck;
    private final String kv;

    ExchangeResult(String key, String ck, String kv) {
      this.key = key;
      this.ck = ck;
      this.kv = kv;
    }

    /** Returns the Base64-encoded shared secret key. */
    public String getKey() {
      return key;
    }

    /** Returns the Base64-encoded encrypted client public key. */
    public String getCk() {
      return ck;
    }

    /** Returns the key version (first 8 chars of {@code MD5(ck)}). */
    public String getKv() {
      return kv;
    }

    @Override
    public String toString() {
      return "ExchangeResult{kv='" + kv + "'}";
    }
  }
}
