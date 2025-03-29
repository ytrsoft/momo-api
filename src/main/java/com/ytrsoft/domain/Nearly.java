package com.ytrsoft.domain;

import java.util.ArrayList;
import java.util.List;

public class Nearly {

    @Alias("momoid")
    private String id;

    private String sign;

    @Select("signex.desc")
    private String desc;

    private String name;

    private String sex;

    private Integer age;

    private Integer distance;

    private String relation;

    private String constellation;

    @Alias("show_location")
    private String location;

    @Select("realAuth.status")
    private Integer realAuth;

    @Select("vip.active_level")
    private Integer vip;

    @Select("svip.active_level")
    private Integer svip;

    private String client;

    private List<String> photos = new ArrayList<>();

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRealAuth() {
        return realAuth;
    }

    public void setRealAuth(Integer realAuth) {
        this.realAuth = realAuth;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getSvip() {
        return svip;
    }

    public void setSvip(Integer svip) {
        this.svip = svip;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
