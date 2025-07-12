package com.codeninja2000.pixelbit;

import java.util.ArrayDeque;
// TODO: Reference "Exception Handling in Commands" before starting
public class CommandManager {
    private ArrayDeque<ImageCommand> undoStack;
    private ArrayDeque<ImageCommand> redoStack;
    public CommandManager() {}
    public void executeCommand(ImageCommand command) {}
    public void undo() {}
    public void redo() {}
    public void clearHistory() {}
    public boolean canUndo() { return true; }
    public boolean canRedo() { return true; }
}
