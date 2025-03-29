package com.ytrsoft.domain;

import java.util.ArrayList;
import java.util.List;

public class Comment {

    @Alias("feedid")
    private String id;

    @Alias("toname")
    private String name;

    private String owner;

    @Alias("comment_distance")
    private Integer distance;

    @Alias("like_count")
    private Integer liked;

    @Alias("create_time")
    private Long time;

    @Select("user.momoid")
    private String uid;

    @Select("user.sex")
    private String usex;

    @Select("user.age")
    private String uage;

    @Select("user.vip.active_level")
    private Integer vip;

    @Select("user.svip.active_level")
    private Integer svip;

    @Select("user.name")
    private String uname;

    private String content;

    private List<Comment> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUage() {
        return uage;
    }

    public void setUage(String uage) {
        this.uage = uage;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
