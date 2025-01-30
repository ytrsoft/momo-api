package com.ytrsoft.entity;

public class Commit {

    private String id;
    private String src;
    private String to;
    private String content;

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getSrc() {
        return src;
    }

    public String getTo() {
        return to;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTo(String to) {
        this.to = to;
    }
}