package com.ytrsoft.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "momo")
public class Props {

    private String ua;
    private String account;
    private String cookie;
    private final CheckOS checkOS;
    private final KeyExchange.ExchangeResult result;
    private static final boolean isOS = true;


    private Props() {
        this.checkOS = new CheckOS();
        this.result = KeyExchange.getInstance().execute();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getKey() {
        if (isOS) {
            return this.checkOS.getKey();
        }
        return this.result.getKey();
    }

    public String getKv() {
        if (isOS) {
            return this.checkOS.getKv();
        }
        return this.result.getKv();
    }


    public String getCk() {
        if (isOS) {
            return this.checkOS.getCk();
        }
        return this.result.getCk();
    }

}
