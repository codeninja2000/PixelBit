package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.PBModel;

public class UndoCommand implements PBCommand{

    private final PBModel model;

    public UndoCommand(PBModel model) {
        this.model = model;
    }

    /**
     * @throws CommandExecException
     */
    @Override
    public void execute() throws CommandExecException {
        model.undo();
    }

    /**
     *
     */
    @Override
    public void undo() {

    }
}
