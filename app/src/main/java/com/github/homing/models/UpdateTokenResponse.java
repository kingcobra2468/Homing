package com.github.homing.models;

/**
 * The type Update token response.
 */
public class UpdateTokenResponse {
    /**
     * The Success.
     */
    boolean success;
    /**
     * The success of the request.
     */
    String error;

    /**
     * Instantiates a new Update token response.
     *
     * @param success the success
     * @param error   the error
     */
    public UpdateTokenResponse(boolean success, String error) {
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
