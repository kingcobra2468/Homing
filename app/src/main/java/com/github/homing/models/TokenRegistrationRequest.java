package com.github.homing.models;

import com.google.gson.annotations.SerializedName;

/**
 * The type Token registration request.
 */
public class TokenRegistrationRequest {
    /**
     * The Registration token.
     */
    @SerializedName("registration_token")
    String registrationToken;

    /**
     * Instantiates a new Token registration request.
     *
     * @param registrationToken the registration token
     */
    public TokenRegistrationRequest(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
