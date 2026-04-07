package com.momo.sdk.crypto;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

/**
 * 与 Momo 服务器进行 ECDH 密钥协商。
 *
 * <p>使用单例模式,避免重复注册 BouncyCastle Provider。每次调用 {@link #execute()} 都会
 * 生成一对全新的临时 EC 密钥对,与硬编码的服务端公钥协商出一次性主密钥。
 */
@Log
public final class KeyExchange {

  /** 用于客户端公钥再次加密的偏移密钥。 */
  private static final String OFFSET = "Iu0WKHFy";

  /** 椭圆曲线名称。 */
  private static final String EC_CURVE = "secp192k1";

  /** 服务端固定公钥(未压缩点格式)。 */
  private static final byte[] SERVER_PUBLIC_KEY = {
    4, -59, 23, 69, 30, 95, 105, 75, 121, -35, -97, -23, -51, -76, -108, 121,
    34, 87, -20, -31, -125, 17, 35, 64, -126, -12, 64, -44, -79, 51, -93, -38,
    116, -119, -80, 82, 88, 5, 2, -13, 70, 77, 28, -1, -115, 28, 101, -85, -8
  };

  private static volatile KeyExchange instance;

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  private KeyExchange() {}

  /**
   * 获取 KeyExchange 单例。
   *
   * @return 单例实例
   */
  public static KeyExchange getInstance() {
    if (instance == null) {
      synchronized (KeyExchange.class) {
        if (instance == null) {
          instance = new KeyExchange();
        }
      }
    }
    return instance;
  }

  /**
   * 执行一次密钥协商。
   *
   * @return 协商结果;若过程中发生异常则返回字段全为 {@code null} 的对象
   */
  public KeyExchangeResult execute() {
    KeyExchangeResult result = new KeyExchangeResult();
    try {
      ECParameterSpec curveParams = curveParameters();
      KeyPair keyPair = generateKeyPair();
      ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
      ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

      ECPoint serverPoint = curveParams.getCurve().decodePoint(SERVER_PUBLIC_KEY);
      ECDHBasicAgreement agreement = initEcdh(curveParams, privateKey);
      byte[] sharedSecret = computeSharedSecret(curveParams, agreement, serverPoint);

      byte[] encodedPublicKey = publicKey.getQ().getEncoded(false);
      byte[] wrappedPublicKey = CryptoUtils.encode(encodedPublicKey, OFFSET.getBytes());

      String ck = Base64Utils.encode(wrappedPublicKey);
      String key = Base64Utils.encode(sharedSecret);
      String kv = CryptoUtils.md5(ck).substring(0, 8);

      result.setKey(key);
      result.setCk(ck);
      result.setKv(kv);
    } catch (Exception e) {
      log.log(Level.SEVERE, "密钥协商失败: {0}", e.getMessage());
    }
    return result;
  }

  /** 取得指定曲线的参数。 */
  private ECParameterSpec curveParameters() {
    X9ECParameters params = ECNamedCurveTable.getByName(EC_CURVE);
    return new ECParameterSpec(params.getCurve(), params.getG(), params.getN(), params.getH());
  }

  /** 生成临时 EC 密钥对。 */
  private KeyPair generateKeyPair() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("EC", "BC");
    generator.initialize(new ECGenParameterSpec(EC_CURVE));
    return generator.generateKeyPair();
  }

  /** 初始化 ECDH 协商对象并装入私钥。 */
  private ECDHBasicAgreement initEcdh(ECParameterSpec curveParams, ECPrivateKey privateKey) {
    ECDHBasicAgreement agreement = new ECDHBasicAgreement();
    agreement.init(
        new ECPrivateKeyParameters(
            privateKey.getD(),
            new ECDomainParameters(
                curveParams.getCurve(),
                curveParams.getG(),
                curveParams.getN(),
                curveParams.getH())));
    return agreement;
  }

  /** 计算共享密钥并修剪可能的符号位。 */
  private byte[] computeSharedSecret(
      ECParameterSpec curveParams, ECDHBasicAgreement agreement, ECPoint publicPoint) {
    BigInteger sharedSecret =
        agreement.calculateAgreement(
            new ECPublicKeyParameters(
                publicPoint,
                new ECDomainParameters(
                    curveParams.getCurve(),
                    curveParams.getG(),
                    curveParams.getN(),
                    curveParams.getH())));
    byte[] bytes = sharedSecret.toByteArray();
    return (bytes[0] == 0) ? Arrays.copyOfRange(bytes, 1, bytes.length) : bytes;
  }
}
