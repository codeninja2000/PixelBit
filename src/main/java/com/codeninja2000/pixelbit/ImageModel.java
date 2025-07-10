public class ImageModel {
    private EditableImage image;
    private boolean isModified;
    public ImageModel() {}
    public EditableImage getImage() { return new EditableImage(); }
    public void setImage() {};
    public boolean isModified() { return true; }
    public void setModified(boolean modified) {}
}
