package com.pixelbit.model;
import java.awt.image.BufferedImage;

public interface ImageHistory {
    void saveState(BufferedImage image);
    BufferedImage undo(BufferedImage current);
    BufferedImage redo(BufferedImage current);
    boolean canUndo();
    boolean canRedo();
    void clear();
}
