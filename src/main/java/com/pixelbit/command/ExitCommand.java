package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import javafx.application.Platform;

public class ExitCommand implements PBCommand{
    /**
     * @throws CommandExecException
     */
    @Override
    public void execute() throws CommandExecException {
        Platform.exit();

    }

    /**
     *
     */
    @Override
    public void undo() {

    }
}
