package com.github.homing.models;

public class HeartbeatResponse {
    boolean success;
    String error;

    public HeartbeatResponse(boolean success, String error) {
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
