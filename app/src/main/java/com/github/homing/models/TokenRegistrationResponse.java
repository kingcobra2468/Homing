package com.github.homing.models;

/**
 * The type Token registration response.
 */
public class TokenRegistrationResponse {
    /**
     * The Success.
     */
    boolean success;
    /**
     * The success of the request.
     */
    Object error;

    /**
     * Instantiates a new Token registration response.
     *
     * @param success the success
     * @param error   the error
     */
    public TokenRegistrationResponse(boolean success, String error) {
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
    public Object getError() {
        return error;
    }
}
