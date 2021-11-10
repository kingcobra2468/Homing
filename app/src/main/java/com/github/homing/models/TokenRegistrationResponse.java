package com.github.homing.models;

public class TokenRegistrationResponse {
    boolean success;
    String error;

    public TokenRegistrationResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
