package com.ytrsoft.core;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

public class KeyExchange {

    private static final String OFFSET = "Iu0WKHFy";
    private static final String EC_CURVE = "secp192k1";
    private static KeyExchange INSTANCE;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final byte[] SERVER_PUBLIC_KEY = {
        4, -59, 23, 69, 30, 95, 105, 75, 121, -35, -97, -23, -51, -76, -108, 121, 34, 87,
        -20, -31, -125, 17, 35, 64, -126, -12, 64, -44, -79, 51, -93, -38, 116, -119, -80,
        82, 88, 5, 2, -13, 70, 77, 28, -1, -115, 28, 101, -85, -8
    };

    private static final Logger logger = LoggerFactory.getLogger(KeyExchange.class);

    private KeyExchange() {}

    public static KeyExchange getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KeyExchange();
        }
        return INSTANCE;
    }

    public static class ExchangeResult {
        private String key;
        private String ck;
        private String kv;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCk() {
            return ck;
        }

        public void setCk(String ck) {
            this.ck = ck;
        }

        public String getKv() {
            return kv;
        }

        public void setKv(String kv) {
            this.kv = kv;
        }
    }

    private ECParameterSpec getCurveParameters() {
        X9ECParameters params = ECNamedCurveTable.getByName(EC_CURVE);
        return new ECParameterSpec(
            params.getCurve(),
            params.getG(),
            params.getN(),
            params.getH()
        );
    }

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec(EC_CURVE));
        return keyPairGenerator.generateKeyPair();
    }

    private ECDHBasicAgreement initECDH(ECParameterSpec curveParams, ECPrivateKey privateKey) {
        ECDHBasicAgreement agreement = new ECDHBasicAgreement();
        agreement.init(new ECPrivateKeyParameters(
            privateKey.getD(),
            new ECDomainParameters(
                curveParams.getCurve(),
                curveParams.getG(),
                curveParams.getN(),
                curveParams.getH()
            )
        ));
        return agreement;
    }

    private byte[] computeSharedSecret(ECParameterSpec curveParams, ECDHBasicAgreement agreement, ECPoint publicPoint) {
        BigInteger sharedSecret = agreement.calculateAgreement(new ECPublicKeyParameters(
            publicPoint,
            new ECDomainParameters(
                curveParams.getCurve(),
                curveParams.getG(),
                curveParams.getN(),
                curveParams.getH()
            )
        ));
        return sharedSecret.toByteArray();
    }

    public ExchangeResult execute() {
        ExchangeResult result = new ExchangeResult();
        try {
            ECParameterSpec curveParams = getCurveParameters();
            KeyPair keyPair = generateKeyPair();
            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
            ECPoint serverPublicPoint = curveParams.getCurve().decodePoint(SERVER_PUBLIC_KEY);
            ECDHBasicAgreement agreement = initECDH(curveParams, privateKey);
            byte[] sharedSecret = computeSharedSecret(curveParams, agreement, serverPublicPoint);
            byte[] encodedPublicKey = publicKey.getQ().getEncoded(false);
            byte[] encodedWithOffset = Coded.encode(encodedPublicKey, OFFSET.getBytes());
            String CK = Base64.encode(encodedWithOffset);
            String KEY = Base64.encode(sharedSecret);
            String KV = Coded.md5(CK).substring(0, 8);
            result.setKey(KEY);
            result.setCk(CK);
            result.setKv(KV);
        } catch (Exception e) {
            logger.error("密钥交换失败: {}", e.getMessage());
        }
        return result;
    }
}
