package com.udacity.jwdnd.course1.cloudstorage.enums;

public enum ServiceResponse {
    SUCCESS("success"),
    USER_NOT_FOUND_ERROR("user not found"),
    RECORD_NOT_FOUND_ERROR("record not found"),
    DATABASE_ERROR("database error"),
    ENCRYPTION_ERROR("encryption error");

    private final String status;

    ServiceResponse(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }
}
