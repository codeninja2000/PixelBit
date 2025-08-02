package com.pixelbit.exception;

/**
 * Exception thrown when invalid parameters are provided for a filter.
 * This can occur if the parameters do not match the expected types or count.
 */
public class InvalidFilterParamsException extends IllegalArgumentException {

    /**
     * Constructs a new InvalidFilterParamsException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidFilterParamsException(String message) {
        super(message);
    }
    /**
     * Constructs a new InvalidFilterParamsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public InvalidFilterParamsException(String message, Throwable cause) {
        super(message, cause);
    }
}
