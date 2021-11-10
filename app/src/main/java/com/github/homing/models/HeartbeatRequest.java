package com.github.homing.models;

import com.google.gson.annotations.SerializedName;

public class HeartbeatRequest {
    @SerializedName("registration_token")
    String registrationToken;

    public HeartbeatRequest(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
