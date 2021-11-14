package com.github.homing.models;

import com.google.gson.annotations.SerializedName;

/**
 * Ucrs Heartbeat request.
 */
public class HeartbeatRequest {
    /**
     * The Registration token.
     */
    @SerializedName("registration_token")
    String registrationToken;

    /**
     * Instantiates a new Heartbeat request.
     *
     * @param registrationToken the registration token
     */
    public HeartbeatRequest(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
