package com.qa.api.exceptions;

/**
 * ValidationException - Custom exception for validation failures
 * Thrown when API response validation fails
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructor with message
     * @param message Exception message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Exception message
     * @param cause Root cause exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause only
     * @param cause Root cause exception
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
