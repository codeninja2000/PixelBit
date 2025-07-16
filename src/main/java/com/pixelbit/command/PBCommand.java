package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;

/**
 * PBCommand interface defines the contract for commands in the PixelBit application.
 * Each command must implement the execute method to perform its action and the undo method
 * to revert that action.
 */
public interface PBCommand {
    /**
     * Executes the command.
     * This method should contain the logic to perform the action associated with the command.
     *
     * @throws CommandExecException if an error occurs during command execution
     */
   void execute() throws CommandExecException;

    /**
     * Undoes the command.
     * This method should contain the logic to revert the action performed by the execute method.
     */
   void undo();

}