package com.ytrsoft.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "momo")
public class Props {

    private String id;
    private String ua;
    private String sign;
    private static boolean locked = false;
    private KeyExchange.ExchangeResult result = null;

    private Props() {
        if (!locked) {
            exchange();
            locked = true;
        }
    }

    public void exchange() {
        this.result = KeyExchange.getInstance().execute();
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getKey() {
        return this.result.getKey();
    }

    public String getKv() {
        return this.result.getKv();
    }

    public String getCk() {
        return this.result.getCk();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
