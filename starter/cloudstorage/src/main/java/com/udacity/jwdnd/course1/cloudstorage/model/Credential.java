package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer id;
    private String url;
    private String userName;
    private String key;
    private String password;
    private Integer userId;

    public Credential(Integer id, String url, String userName, String key, String password, Integer userId) {
        this.id = id;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Credential copy(Credential credential){
        return new Credential(
                credential.id,
                credential.url,
                credential.userName,
                credential.key,
                credential.password,
                credential.userId);
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}


