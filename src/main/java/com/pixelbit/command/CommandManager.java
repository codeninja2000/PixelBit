package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.model.filter.FilterType;
import com.pixelbit.view.UIErrorNotifier;

import java.util.ArrayDeque;
import java.util.Map;

// TODO: Redo exception handling so that a CommandExecException is thrown and handled further up the stack (like in the controller).

/**
 * CommandManager is responsible for managing the execution, undo, and redo of commands.
 * It maintains two stacks: one for undo operations and another for redo operations.
 * It also provides methods to check if undo or redo operations are possible.
 * It can optionally notify the UI of errors during command execution.
 */
public class CommandManager {

    private final ArrayDeque<PBCommand> undoStack = new ArrayDeque<>();
    private final ArrayDeque<PBCommand> redoStack = new ArrayDeque<>();

    private final FilterFactory filterFactory;
private UIErrorNotifier ui;
/**
     * Optional UI notifier to display error messages.
     * If null, errors will not be displayed in the UI.
     */


    public CommandManager(FilterFactory filterFactory) {
        this(filterFactory, null);

    }
        public CommandManager(FilterFactory filterFactory, UIErrorNotifier ui) {
        this.filterFactory = filterFactory;
        this.ui = ui;
    }


    public FilterFactory getFilterFactory() {
        return filterFactory;
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
            if (ui != null) {
                ui.showError("Error executing command: " + e.getMessage());
            }
        }
    }

    public void executeApplyFilterCommand(EditableImage image, FilterType filterType, Map<String, Object> params) {
        ApplyFilterCommand command = new ApplyFilterCommand(image,filterFactory, filterType, params);
        executeCommand(command);
    }


    /**
     * Undoes the last executed command, if possible.
     * If an error occurs during undo, it notifies the UI if a UIErrorNotifier is set.
     */
    public void undo() {
        if (canUndo()) {
            try {
            PBCommand command = undoStack.pop();
                command.undo();
                redoStack.push(command);
            } catch (Exception e) {
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
            try {
            PBCommand command = redoStack.pop();
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


