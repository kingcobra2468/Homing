package com.github.homing.models;

/**
 * Ucrs Heartbeat response.
 */
public class HeartbeatResponse {
    /**
     * The success of the request.
     */
    boolean success;
    /**
     * The success of the request.
     */
    String error;

    /**
     * Instantiates a new Heartbeat response.
     *
     * @param success the success
     * @param error   the error
     */
    public HeartbeatResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    /**
     * Is the request to ucrs successful and processed without any errors.
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }
}
