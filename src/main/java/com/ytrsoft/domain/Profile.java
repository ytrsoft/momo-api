package com.ytrsoft.domain;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    @Alias("show_momoid")
    private String id;

    private String sign;

    private String name;

    private String sex;

    private String height;

    private String regtime;

    @Alias("user_popular_text")
    private String popular;

    @Alias("show_location")
    private String location;

    private Integer age;

    @Select("vip.active_level")
    private Integer vip;

    @Select("svip.active_level")
    private Integer svip;

    @Select("sp_school.name")
    private List<String> school = new ArrayList<>();

    @Select("profile_marks.text_list")
    private List<String> marks = new ArrayList<>();

    @Select("greet_question.question")
    private List<String> question = new ArrayList<>();

    @Select("device_info.device")
    private String device;

    @Select("sp_living.name")
    private String living;

    private String phone;

    @Select("sp_company.name")
    private String company;

    @Select("sp_workplace.name")
    private String workplace;

    @Select("sp_hometown.name")
    private String hometown;

    @Select("sp_industry.name")
    private String job;

    @Select("realAuth.status")
    private Integer realAuth;

    @Select("growup.level")
    private String level;

    private List<String> photos = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public List<String> getSchool() {
        return school;
    }

    public void setSchool(List<String> school) {
        this.school = school;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getRealAuth() {
        return realAuth;
    }

    public void setRealAuth(Integer realAuth) {
        this.realAuth = realAuth;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getMarks() {
        return marks;
    }

    public void setMarks(List<String> marks) {
        this.marks = marks;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public List<String> getQuestion() {
        return question;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
