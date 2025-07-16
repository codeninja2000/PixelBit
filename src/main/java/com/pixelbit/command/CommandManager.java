package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.view.UIErrorNotifier;

import java.util.ArrayDeque;


/**
 * CommandManager is responsible for managing the execution, undo, and redo of commands.
 * It maintains two stacks: one for undo operations and another for redo operations.
 * It also provides methods to check if undo or redo operations are possible.
 * It can optionally notify the UI of errors during command execution.
 */
public class CommandManager {

    private final ArrayDeque<PBCommand> undoStack = new ArrayDeque<>();
    private final ArrayDeque<PBCommand> redoStack = new ArrayDeque<>();
    private UIErrorNotifier ui; /**
     * Optional UI notifier to display error messages.
     * If null, errors will not be displayed in the UI.
     */


    public CommandManager() {
        this.ui = null; // Default constructor without UI notifier
    }

    /**
     * Constructor that allows passing a UIErrorNotifier to display errors.
     *
     * @param ui UIErrorNotifier instance to handle error notifications.
     */
    public CommandManager(UIErrorNotifier ui) {
        this.ui = ui;

    }

    /**
     * Gets the current UIErrorNotifier instance.
     *
     * @return the UIErrorNotifier instance used for error notifications.
     */
    public UIErrorNotifier getUi() {
        return ui;
    }

    /**
     * Sets the UIErrorNotifier to be used for error notifications.
     *
     * @param ui UIErrorNotifier instance to handle error notifications.
     */
    public void setUi(UIErrorNotifier ui) {
        this.ui = ui;
    }

    /**
     * Executes a command, pushing it onto the undo stack.
     * If an error occurs during execution, it notifies the UI if a UIErrorNotifier is set.
     *
     * @param command The command to execute.
     */
    public void executeCommand(PBCommand command) {
        try {
            command.execute();
            undoStack.push(command);
            redoStack.clear(); // Clear redo stack on new command execution
        } catch (CommandExecException e) {
            //System.err.println("Error executing command: " + e.getMessage());
            // TODO: Add logging for developers
            if (ui != null) {
                ui.showError("Error executing command: " + e.getMessage());
            }
        }
    }

    /**
     * Undoes the last executed command, if possible.
     * If an error occurs during undo, it notifies the UI if a UIErrorNotifier is set.
     */
    public void undo() {
        if (canUndo()) {
            PBCommand command = undoStack.pop();
            try {
                command.undo();
                redoStack.push(command);
            } catch (Exception e) {
                //System.err.println("Error undoing command: " + e.getMessage());
                // TODO: Add logging for developers
                if (ui != null) {
                    ui.showError("Error undoing command: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Redoes the last undone command, if possible.
     * If an error occurs during redo, it notifies the UI if a UIErrorNotifier is set.
     */
    public void redo() {
        if (canRedo()) {
            PBCommand command = redoStack.pop();
            try {
                command.execute();
                undoStack.push(command);
            } catch (Exception e) {
                System.err.println("Error redoing command: " + e.getMessage());
                // TODO: Add logging for developers
                if (ui != null) {
                    ui.showError("Error redoing command: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Clears the command history.
     */
    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }

    /**
     * Checks if there are commands available to undo.
     *
     * @return true if there are commands in the undo stack, false otherwise.
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
