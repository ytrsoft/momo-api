package com.ytrsoft.domain;

import java.util.ArrayList;
import java.util.List;

public class News {

    private String id;

    @Select("basicInfo.sex")
    private String sex;

    @Select("basicInfo.name")
    private String name;

    @Select("basicInfo.age")
    private Integer age;

    @Select("basicInfo.realAuth.status")
    private Integer realAuth;

    @Select("basicInfo.relation")
    private String relation;

    @Select("contentData.owner")
    private String uid;

    @Select("contentData.content")
    private String content;

    @Select("basicInfo.avatarUrl")
    private String avatar;

    @Select("contentData.microvideo.video")
    private String video;

    @Select("markText.markLocDistance.text")
    private String markLocDistance;

    @Select("markText.markTime.text")
    private String markTime;

    @Select("consoleInfo.likeInfo.liked")
    private Integer liked;

    @Select("consoleInfo.commentInfo.commentCount")
    private Integer comment;

    @Select("consoleInfo.forwardInfo")
    private Integer forward;

    @Select("contentData.pics")
    private List<String> pics = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Integer getRealAuth() {
        return realAuth;
    }

    public void setRealAuth(Integer realAuth) {
        this.realAuth = realAuth;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMarkLocDistance() {
        return markLocDistance;
    }

    public void setMarkLocDistance(String markLocDistance) {
        this.markLocDistance = markLocDistance;
    }

    public String getMarkTime() {
        return markTime;
    }

    public void setMarkTime(String markTime) {
        this.markTime = markTime;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
