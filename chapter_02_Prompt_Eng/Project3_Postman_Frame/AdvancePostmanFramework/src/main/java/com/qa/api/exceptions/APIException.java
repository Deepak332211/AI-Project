package com.qa.api.exceptions;

/**
 * APIException - Custom exception for API-related failures
 * Thrown when API request/response operations fail
 */
public class APIException extends RuntimeException {

    /**
     * Constructor with message
     * @param message Exception message
     */
    public APIException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Exception message
     * @param cause Root cause exception
     */
    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause only
     * @param cause Root cause exception
     */
    public APIException(Throwable cause) {
        super(cause);
    }
}
