package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;

public interface PBCommand {
   void execute() throws CommandExecException;
   void undo();

}