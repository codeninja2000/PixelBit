package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.PBModel;

public class RedoCommand implements PBCommand{

    private final PBModel model;

    public RedoCommand(PBModel model) {
        this.model = model;
    }


    /**
     * @throws CommandExecException
     */
    @Override
    public void execute() throws CommandExecException {
        model.redo();
    }

    /**
     *
     */
    @Override
    public void undo() {

    }
}
