package com.hilderjares.spring.security;

public class Response {

    private String value;
    private int code;

    public Response(String value, int code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public int getCode() {
        return this.code;
    }
}
