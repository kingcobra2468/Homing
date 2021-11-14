package com.github.homing.models;

import com.google.gson.annotations.SerializedName;

/**
 * The type Update token request.
 */
public class UpdateTokenRequest {
    /**
     * The old registration token.
     */
    @SerializedName("old_token")
    String oldToken;
    /**
     * The new registration token.
     */
    @SerializedName("new_token")
    String newToken;

    /**
     * Instantiates a new Update token request.
     *
     * @param oldToken the old token
     * @param newToken the new token
     */
    public UpdateTokenRequest(String oldToken, String newToken) {
        this.oldToken = oldToken;
        this.newToken = newToken;
    }
}
