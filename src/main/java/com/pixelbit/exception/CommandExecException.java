package com.pixelbit.exception;

/**
 * CommandExecException is thrown when there is an error during the execution of a command.
 * This exception can be used to indicate issues such as command failure, invalid parameters,
 * or any other execution-related problems.
 */
public class CommandExecException extends Exception {

    /**
     * Constructs a new CommandExecException with the specified detail message.
     *
     * @param message the detail message
     */
    public CommandExecException(String message) {
        super(message);
    }

    /**
     * Constructs a new CommandExecException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public CommandExecException(String message, Throwable cause) {
        super(message, cause);
    }
}
