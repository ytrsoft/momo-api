package com.ytrsoft.core;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.Security;

public class CheckOS {

    private static final String KEY = "gemWgpuj#*su@kgemW";
    private static final String IV = "GUgemWNhGTrh6kSM";
    private static final String AES_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String SHA256_ALGORITHM = "SHA-256";

    private final String ck;
    private final String kv;
    private final String key;

    public CheckOS() {
        JSONObject parse = parse();
        this.key = parse.getString("_s");
        this.kv = parse.getString("_h");
        this.ck = parse.getString("_p");
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private byte[] sha256() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256_ALGORITHM);
            messageDigest.update(KEY.getBytes());
            return messageDigest.digest();
        } catch (Exception unused) {
            return new byte[20];
        }
    }

    private JSONObject parse() {
        try {
            String body = Utilize.readCheckOS();
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            SecretKeySpec sks = new SecretKeySpec(sha256(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, sks, ips);
            byte[] bytes = Base64.decode(body.getBytes());
            byte[] result = cipher.doFinal(bytes);
            byte[] decode = Base64.decode(result);
            return new JSONObject(new String(decode));
        } catch (Exception unused) {
            return new JSONObject();
        }
    }

    public String getKey() {
        return this.key;
    }

    public String getKv() {
        return this.kv;
    }

    public String getCk() {
        return ck;
    }
}
