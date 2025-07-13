package com.pixelbit.model;

import java.awt.image.BufferedImage;

public class PBModel {
    private EditableImage image;
    private ImageHistory history;
    private boolean isModified;

    public PBModel() {}
    public EditableImage getImage() { return image; }
    public void setImage(EditableImage image) {
        this.image = image;
        history = new TransientImageHistory();
        history.saveState(image.getBufferedImage());
    };
    public boolean isModified() { return isModified; }
    public void resetModifiedFlag() {
        isModified = false;
    }
    public void applyEdit(BufferedImage image) {
        history.saveState(image);
        this.image.setImage(image);
    }
    public void undo() {
        image.setImage(history.undo(image.getBufferedImage()));
    }
    public void redo() {
        image.setImage(history.redo(image.getBufferedImage()));
    }
    public boolean canUndo() {
        return history.canUndo();
    }
    public boolean canRedo() {
        return history.canRedo();
    }
}
