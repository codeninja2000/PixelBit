package com.pixelbit.exception;

/**
 * CommandExecException is thrown when there is an error during the execution of a command.
 * This exception can be used to indicate issues such as command failure, invalid parameters,
 * or any other execution-related problems.
 */
public class CommandExecException extends Exception {
    public CommandExecException(String message) {
        super(message);
    }
    public CommandExecException(String message, Throwable cause) {
        super(message, cause);
    }
}
