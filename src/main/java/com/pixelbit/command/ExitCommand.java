package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import javafx.application.Platform;

/**
 * ExitCommand is a command that exits the application.
 * It implements the PBCommand interface.
 */
public class ExitCommand implements PBCommand{
    /**
     * @throws CommandExecException if an error occurs during execution
     */
    @Override
    public void execute() throws CommandExecException {
        Platform.exit();

    }

    /**
     * This method is not implemented for ExitCommand.
     */
    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo operation is not supported for ExitCommand.");
    }
}
