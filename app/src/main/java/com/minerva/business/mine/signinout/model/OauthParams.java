package com.minerva.business.mine.signinout.model;

public class OauthParams {
    private String uid;
    private String type;
    private String token;
    private String name;
    private String union_id;
    private String image;
    private String from;

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnion_id() {
        return union_id == null ? "" : union_id;
    }

    public void setUnion_id(String union_id) {
        this.union_id = union_id;
    }

    public String getImage() {
        return image == null ? "" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFrom() {
        return from == null ? "" : from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
