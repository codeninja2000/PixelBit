package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.view.UIErrorNotifier;

import java.util.ArrayDeque;


public class CommandManager {
    private ArrayDeque<PBCommand> undoStack = new ArrayDeque<>();

    private ArrayDeque<PBCommand> redoStack = new ArrayDeque<>();

    private UIErrorNotifier ui;

    public CommandManager() {
        this.ui = null; // Default constructor without UI notifier
    }

    public CommandManager(UIErrorNotifier ui) {
        this.ui = ui;
    }

    public void executeCommand(PBCommand command) {
        try {
            command.execute();
            undoStack.push(command);
            redoStack.clear(); // Clear redo stack on new command execution
        } catch (CommandExecException e) {
            //System.err.println("Error executing command: " + e.getMessage());
            // TODO: Add logging for developers
            ui.showError("Error executing command: " + e.getMessage());
        }
    }

    public void undo() {
        if (canUndo()) {
            PBCommand command = undoStack.pop();
            try {
                command.undo();
                redoStack.push(command);
            } catch (Exception e) {
                //System.err.println("Error undoing command: " + e.getMessage());
                // TODO: Add logging for developers
                ui.showError("Error undoing command: " + e.getMessage());
            }
        }
    }

    public void redo() {
        if (canRedo()) {
            PBCommand command = redoStack.pop();
            try {
                command.execute();
                undoStack.push(command);
            } catch (Exception e) {
                System.err.println("Error redoing command: " + e.getMessage());
                // TODO: Add logging for developers
                ui.showError("Error redoing command: " + e.getMessage());
            }
        }
    }

    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
