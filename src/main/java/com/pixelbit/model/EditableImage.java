package com.pixelbit.model;


import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;


/**
 * EditableImage is a class that represents an image that can be edited.
 * It contains methods to manipulate the image, such as getting and setting pixels,
 * and it can be initialized from a file path or created with a new BufferedImage.
 */
public class EditableImage {

    /** Default values for image properties */
    public static final String DEFAULT_IMAGE_FILENAME = "default";
    public static final String DEFAULT_IMAGE_FORMAT = "jpg";
    public static final int DEFAULT_IMAGE_SIZE = 1;

    private BufferedImage image;
    private BufferedImage originalImage; // Store the original image
    private String filename = DEFAULT_IMAGE_FILENAME;
    private String format = DEFAULT_IMAGE_FORMAT;
    final private long createdAt = System.currentTimeMillis();

    /**
     * Default constructor that initializes an empty EditableImage with a 1x1 pixel image.
     * This is useful for creating a placeholder image that can be modified later.
     */
    public EditableImage() {
        this.image = new BufferedImage(DEFAULT_IMAGE_SIZE, EditableImage.DEFAULT_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Constructor that inititalizes an EditableImage from a buffered image.
     *
     * @param image instance of BufferedImage
     * @param filename name of image file
     * @param format format of the image file (e.g., "jpg", "png", "gif").
     * @see java.awt.image.BufferedImage
     */
    public EditableImage(BufferedImage image, String filename, String format) {
        this.image = image;
        this.filename = filename;
        this.format = format;
    }

    /**
     * Constructor that initializes an EditableImage from a buffered image.
     *
     * @param image instance of BufferedImage
     * @param filename name of image file
     */
    public EditableImage(BufferedImage image, String filename) {
        this.image = image;
        this.filename = filename;
    }

    /**
     * Constructor that initializes an EditableImage from a buffered image.
     * This constructor is used when the image is created without a specific filename or format.
     *
     * @param image instance of BufferedImage
     */
    public EditableImage(BufferedImage image) {
        this.image = image;
        // Keep a deep copy of the original image
        this.originalImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        this.originalImage.getGraphics().drawImage(image, 0, 0, null);
    }


    /**
     * Creates a deep copy of the current EditableImage instance.
     * The deep copy includes a new BufferedImage and copies the
     * filename and format properties to the new instance.
     *
     * @return a new EditableImage object that is a deep copy of the current instance
     */
    public EditableImage deepCopy() {
        BufferedImage deepCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        deepCopy.getGraphics().drawImage(image,  0, 0, null);
        return new EditableImage(deepCopy, filename, format);
    }

    /**
     * Checks if the image is empty.
     * @return true if the image is null or has a width or height of 1 pixel, false otherwise.
     */
    public boolean isEmpty() {
        if (image == null) {
            return true;
        }
        return image.getWidth() == 1 || image.getHeight() == 1;
    }

    /**
     * Returns the filename of the image.
     * @return the filename of the image, or "default" if not set.
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Sets the filename of the image.
     * @param filename the new filename to set for the image.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Returns the format of the image.
     * @return the format of the image, or "jpg" if not set.
     */
    public String getFormat() {
        return this.format;
    }
    /**
     * Sets the format of the image.
     * @param format the new format to set for the image (e.g., "jpg", "png", "gif").
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Returns the creation time of the image.
     * @return the creation time in milliseconds since epoch.
     */
    public long getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Returns the height of the image.
     * @return the height of the image in pixels.
     */
    public int getHeight() {
        return this.image.getHeight();
    }

    /**
     * Returns the width of the image.
     * @return the width of the image in pixels.
     */
    public int getWidth() {
        return this.image.getWidth();
    }

    /**
     * Returns the BufferedImage object representing the image.
     * @return the BufferedImage instance of the image.
     */
    public BufferedImage getBufferedImage() {
        return this.image;
    }

    /**
     * Converts the BufferedImage to a JavaFX Image.
     * This method is useful for displaying the image in JavaFX applications.
     *
     * @return a JavaFX Image object representing the BufferedImage, or null if the image is null
     */
    public Image toJavaFXImage() {
        // Convert BufferedImage to JavaFX Image
        if (this.image == null) {
            return null; // Handle the case where the image is null
        }
        // Use SwingFXUtils to convert BufferedImage to JavaFX Image

        return SwingFXUtils.toFXImage(this.image, null);
    }

    /**
     * Sets the BufferedImage object for this EditableImage.
     * This method allows you to replace the current image with a new one.
     *
     * @param image the new BufferedImage to set for this EditableImage
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Returns the RGB value of a pixel at the specified coordinates.
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the RGB value of the pixel at (x, y)
     */
    public int getRGB(int x, int y) {
        return image.getRGB(x, y);
    }

    /**
     * Sets the RGB value of a pixel at the specified coordinates.
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @param value the RGB value to set for the pixel
     */
    public void setRGB(int x, int y, int value) {
        image.setRGB(x, y, value);
    }

    /**
     * Returns the original BufferedImage that this EditableImage was created from.
     * This is useful for resetting the image to its original state.
     *
     * @return the original BufferedImage instance
     */
    public BufferedImage getOriginalImage() {
        return originalImage;
    }


    /**
     * Resets the current image to its original state.
     * This method restores the image to the state it was in when this EditableImage was created.
     */
    public void resetToOriginal() {
        this.image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
        this.image.getGraphics().drawImage(originalImage, 0, 0, null);
    }
}