package com.github.homing.models;

import com.google.gson.annotations.SerializedName;

public class TokenRegistrationRequest {
    @SerializedName("registration_token")
    String registrationToken;

    public TokenRegistrationRequest(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
