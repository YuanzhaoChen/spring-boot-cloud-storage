package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private Integer id;
    private String url;
    private String userName;
    private String key;
    private String encryptedPassword;
    private String rawPassword;
    private Integer userId;

    public CredentialForm(Integer id, String url, String userName, String key, String encryptedPassword, String rawPassword, Integer userId) {
        this.id = id;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.encryptedPassword = encryptedPassword;
        this.rawPassword = rawPassword;
        this.userId = userId;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
