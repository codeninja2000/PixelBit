package com.pixelbit.util;

import com.pixelbit.model.EditableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtility {
    public static void main(String[] args) {

    }


        private static final String LOAD_ERROR_MESSAGE = "Image file could not be loaded: %s";

        public static EditableImage loadFromFile(String path) throws IOException {
            return loadFromFile(new File(path));
        }

        public static EditableImage loadFromFile(File file) throws IOException {
            try (var ignored = new FileInputStream(file)) { // verify file is accessible
                BufferedImage image = ImageIO.read(file);
                validateImage(image, file.getAbsolutePath());
                return new EditableImage(image);
            } catch (IOException e) {
                throw new IOException(String.format(LOAD_ERROR_MESSAGE, file.getAbsolutePath()), e);
            }
        }

        private static void validateImage(BufferedImage image, String path) throws IOException {
            if (image == null) {
                throw new IOException(String.format(LOAD_ERROR_MESSAGE + " (unsupported or corrupt image format)", path));
            }
        }


    private static final String DEFAULT_IMAGE_FORMAT = "jpg";
    private static final String SAVE_ERROR_MESSAGE = "Failed to save image to %s: %s";

    public static void saveToFile(EditableImage image, String filepath) throws IOException {
        saveToFile(image, Paths.get(filepath).toFile());
    }

    public static void saveToFile(EditableImage image, File file) throws IOException {
        String format = getFileExtension(file.getName());

        if (format.isEmpty()) {
            format = DEFAULT_IMAGE_FORMAT;
        }

        try {
            ImageIO.write(image.getBufferedImage(), format, file);
        } catch (IOException e) {
            throw new IOException(String.format(SAVE_ERROR_MESSAGE, file.getAbsolutePath(), e.getMessage()), e);
        }
    }
//    public static void saveToFile(EditableImage image, File file) throws IOException {
//        // Get file name and format
//        String filename = file.getName();
//
//        String format = getFileExtension(filename).toLowerCase();
//
//        if (format.isEmpty()) {
//            format = "jpg"; // Fallback default
//        }
//        try {
//            ImageIO.write(image.getBufferedImage(), format, file);
//        } catch (IOException e) {
//            throw new IOException("Failed to save image: " + e.getMessage(), e);
//        }
//    }



    public static String getFileExtension(String filename) {
        int index = filename.lastIndexOf('.');

        if (index > 0 && index < filename.length() - 1) {
            return filename.substring(index + 1).toLowerCase();
        }
        return "";
    }
//    public static String getFilenameWithoutExtension(String filename) {
//        int index = filename.lastIndexOf('.');
//
//        if (index > 0 && index < filename.length() - 1) {
//            return filename.substring(0, index);
//        }
//        return "";
//    }
}
