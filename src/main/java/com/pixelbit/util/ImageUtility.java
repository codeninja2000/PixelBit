package com.pixelbit.util;

import com.pixelbit.model.EditableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Utility class for loading and saving images with enhanced error handling.
 * This class provides methods to load images from files and save them back,
 * while ensuring that appropriate error messages are provided for common issues.
 * It also supports determining the image format based on the EditableImage object
 * or the file extension, with a fallback to a default format if necessary.
 */
public class ImageUtility {
    // Error messages for loading and saving images
    private static final String LOAD_ERROR_MESSAGE = "Image file could not be loaded: %s";
    private static final String DEFAULT_IMAGE_FORMAT = "jpg";
    private static final String SAVE_ERROR_MESSAGE = "Failed to save image to %s: %s";


    /**
     * Loads an image from a file path.
     *
     * @param path the file path to load the image from
     * @return an EditableImage object containing the loaded image
     * @throws IOException if the file does not exist, is not readable, or is in an unsupported format
     */
    public static EditableImage loadFromFile(String path) throws IOException {
        return loadFromFile(new File(path));
    }

    /**
     * Loads an image from a File object.
     *
     * @param file the File object to load the image from
     * @return an EditableImage object containing the loaded image
     * @throws IOException if the file does not exist, is not readable, or is in an unsupported format
     */
    public static EditableImage loadFromFile(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException(String.format(LOAD_ERROR_MESSAGE + " (file not found)", file.getAbsolutePath()));
        }
        if (!file.canRead()) {
            throw new IOException(String.format(LOAD_ERROR_MESSAGE + " (access denied)", file.getAbsolutePath()));
        }
        
        String path = file.getAbsolutePath();
        BufferedImage image = ImageIO.read(file);
        validateImage(image, path);
        return new EditableImage(image);
    }

    /**
     * Validates that the loaded image is not null.
     *
     * @param image the BufferedImage to validate
     * @param path the file path used for error reporting
     * @throws IOException if the image is null, indicating an unsupported or corrupt format
     */
    private static void validateImage(BufferedImage image, String path) throws IOException {
        if (image == null) {
            throw new IOException(String.format(LOAD_ERROR_MESSAGE + " (unsupported or corrupt image format)", path));
        }
    }

    /**
     * Saves an EditableImage to a file, determining the format based on the image or file extension.
     *
     * @param image the EditableImage to save
     * @param filepath the path of the file to save the image to
     * @throws IOException if the file cannot be created, is not writable, or if saving fails
     */
    public static void saveToFile(EditableImage image, String filepath) throws IOException {
        saveToFile(image, Paths.get(filepath).toFile());
    }

    /**
     * Saves an EditableImage to a specified file.
     *
     * @param image the EditableImage to save
     * @param file the File object representing the destination
     * @throws IOException if the file cannot be created, is not writable, or if saving fails
     */
    public static void saveToFile(EditableImage image, File file) throws IOException {
        // Validate input parameters
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        // Check if parent directory exists and is writable
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException(String.format("Failed to create directory structure for %s", file.getAbsolutePath()));
        }
        
        // Check write permissions
        if (file.exists() && !file.canWrite()) {
            throw new IOException(String.format(SAVE_ERROR_MESSAGE + " (access denied)", file.getAbsolutePath()));
        }

        // Determine format: prefer EditableImage format, fallback to file extension, then default
        String format = image.getFormat();
        if (format == null || format.isEmpty()) {
            format = getFileExtension(file.getName());
            if (format.isEmpty()) {
                format = DEFAULT_IMAGE_FORMAT;
            }
        }

        try {
            boolean success = ImageIO.write(image.getBufferedImage(), format, file);
            if (!success) {
                throw new IOException(String.format("No appropriate writer found for format: %s", format));
            }
        } catch (IOException e) {
            throw new IOException(String.format(SAVE_ERROR_MESSAGE, file.getAbsolutePath(), e.getMessage()), e);
        }
    }

    /**
     * Extracts the file extension from a filename.
     *
     * @param filename the name of the file
     * @return the file extension in lowercase, or an empty string if no extension is found
     */
    public static String getFileExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index > 0 && index < filename.length() - 1) {
            return filename.substring(index + 1).toLowerCase();
        }
        return "";
    }

}