package com.setronica.intern.test.project.exception;

public class NotFoundError {
    private int errorCode;
    private String errorMessage;

    public NotFoundError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
