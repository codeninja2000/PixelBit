package com.pixelbit.exception;

/**
 * Exception thrown when invalid parameters are provided for a filter.
 * This can occur if the parameters do not match the expected types or count.
 */
public class InvalidFilterParamsException extends IllegalArgumentException {
    public InvalidFilterParamsException(String message) {
        super(message);
    }
    public InvalidFilterParamsException(String message, Throwable cause) {
        super(message, cause);
    }
}
