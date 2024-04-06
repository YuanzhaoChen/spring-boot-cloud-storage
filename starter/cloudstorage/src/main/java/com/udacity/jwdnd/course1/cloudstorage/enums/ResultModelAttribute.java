package com.udacity.jwdnd.course1.cloudstorage.enums;

public enum ResultModelAttribute {
    SUCCESS("success"),
    ERROR("error");
    private final String status;

    ResultModelAttribute(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }
}
