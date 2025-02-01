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


    public Props() {
        this.checkOS = new CheckOS();
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
        return this.checkOS.getKey();
    }

    public String getKv() {
        return this.checkOS.getKv();
    }

    public String getCk() {
        return this.checkOS.getCk();
    }

}
