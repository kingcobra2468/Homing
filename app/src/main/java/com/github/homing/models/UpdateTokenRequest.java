package com.github.homing.models;

import com.google.gson.annotations.SerializedName;

public class UpdateTokenRequest {
    @SerializedName("old_token")
    String oldToken;
    @SerializedName("new_token")
    String newToken;

    public UpdateTokenRequest(String oldToken, String newToken) {
        this.oldToken = oldToken;
        this.newToken = newToken;
    }
}
