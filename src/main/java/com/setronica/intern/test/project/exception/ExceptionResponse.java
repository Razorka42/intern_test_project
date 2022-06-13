package com.setronica.intern.test.project.exception;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {
    private int errorCode;
    private String errorMessage;

    public ExceptionResponse(int errorCode, String errorMessage) {
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
