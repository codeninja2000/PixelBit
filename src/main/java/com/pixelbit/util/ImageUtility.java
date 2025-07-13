package com.pixelbit.util;

import com.pixelbit.model.EditableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtility {
    public static EditableImage loadFromFile(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        if (image == null) {
            throw new IOException("Image file could not be loaded: " + path);
        }
        // Extract filename with file extension
      ;

    }
    public static EditableImage loadFromFile(File file) { return new EditableImage(); }
    public static void saveToFile(EditableImage image, String path) {}
    public static void saveToFile(EditableImage image, File file) {}
    public static String getFileExtension(String filename) {
        int index = filename.lastIndexOf('.');

        if (index > 0 && index < filename.length() - 1) {
            return filename.substring(index + 1).toLowerCase();
        }
        return "";
    }
}
