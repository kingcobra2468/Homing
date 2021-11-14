package com.github.homing.network;

/**
 * The interface ucrs callback for developing logic after a request
 * hass been made to ucrs.
 */
public interface UcrsCallback {
    /**
     * On success.
     */
    void onSuccess();

    /**
     * On error.
     *
     * @param code    the code
     * @param message the message
     */
    void onError(int code, String message);
}
