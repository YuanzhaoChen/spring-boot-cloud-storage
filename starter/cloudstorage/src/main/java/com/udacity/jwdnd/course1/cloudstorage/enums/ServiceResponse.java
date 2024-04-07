package com.udacity.jwdnd.course1.cloudstorage.enums;

public enum ServiceResponse {
    SUCCESS("success"),
    USER_NOT_FOUND_ERROR("User not found."),
    RECORD_NOT_FOUND_ERROR("Record not found."),
    DATABASE_ERROR("Database error."),
    ENCRYPTION_ERROR("Encryption error."),
    FILE_UPLOAD_ERROR("Upload error."),
    FILE_SIZE_ERROR("File is empty or too large."),
    FILE_DUPLICATE_ERROR("File already submitted.");
    private final String status;

    ServiceResponse(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }
}
