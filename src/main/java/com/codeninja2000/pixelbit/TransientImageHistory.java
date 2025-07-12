package com.codeninja2000.pixelbit;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

public class TransientImageHistory implements ImageHistory {
    private final Deque<BufferedImage> undoStack = new ArrayDeque<>();
    private final Deque<BufferedImage> redoStack = new ArrayDeque<>();

    private BufferedImage deepCopy(BufferedImage image) {
        if (image != null) {
           return new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        }
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }
    @Override
    public void saveState(BufferedImage image) {
        if (image != null) {
            undoStack.push(deepCopy(image));
            redoStack.clear(); // Clear the redo stack on new action
        }
    }
    @Override
    public BufferedImage undo(BufferedImage current)  {
            if (undoStack.isEmpty()) {
                return current; // No state to undo
            }
            redoStack.push(deepCopy(current));
            return undoStack.pop();
        }
    @Override
    public BufferedImage redo(BufferedImage current)  {
            if (redoStack.isEmpty()) {
                return current; // No state to redo
            }
            undoStack.push(deepCopy(current));
            return redoStack.pop();
        }
    @Override
    public boolean canUndo() {
        return !undoStack.isEmpty();

    }
    @Override
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    @Override
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }

}
