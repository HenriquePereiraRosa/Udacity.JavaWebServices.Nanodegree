package com.udacity.jwdnd.course1.cloudstorage.model.entity;

public class Credential {

    private Integer id;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;

    public Credential(Integer id, String url, String username, String key, String password, Integer userid) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
