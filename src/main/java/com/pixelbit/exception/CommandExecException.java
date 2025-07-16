package com.pixelbit.exception;

public class CommandExecException extends Exception {
    public CommandExecException(String message) {
        super(message);
    }
    public CommandExecException(String message, Throwable cause) {
        super(message, cause);
    }
}
