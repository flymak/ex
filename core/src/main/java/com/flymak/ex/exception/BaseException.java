package com.flymak.ex.exception;

public class BaseException extends RuntimeException {
    protected int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
