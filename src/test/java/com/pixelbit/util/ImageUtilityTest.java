package com.pixelbit.util;

import com.pixelbit.model.EditableImage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilityTest {

    @Test
    void testSaveToFileWithValidPath() throws IOException {
        // Create a sample image and output file path.
        EditableImage sampleImage = new EditableImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        File outputFile = new File("src/test/resources/output_image.jpg");

        // Test saving the image to a valid path.
        ImageUtility.saveToFile(sampleImage, outputFile.getAbsolutePath());
        assertTrue(outputFile.exists(), "The output image file should exist.");
    }



    @Test
    void testSaveToFileWithoutExtension() throws IOException {
        // Create a sample image and set a file path without an extension.
        EditableImage sampleImage = new EditableImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        File fileWithoutExtension = new File("src/test/resources/output_image_no_extension");

        // Test saving and check that the default format is used.
        ImageUtility.saveToFile(sampleImage, fileWithoutExtension.getAbsolutePath());
        assertTrue(fileWithoutExtension.exists(), "The output file should be saved with the default format.");
    }

    @Test
    void testSaveToFileWithInvalidPath() {
        EditableImage sampleImage = new EditableImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        // Use an invalid character in the filename (on Windows)
        String invalidPath = "C:/test_no_permission.jpg";
        
        IOException exception = assertThrows(IOException.class, () -> ImageUtility.saveToFile(sampleImage, invalidPath));
        assertTrue(exception.getMessage().contains("Failed to save image"), "The error message must indicate failure due to invalid path.");
    }


    /**
     * Unit tests for the getFileExtension method in ImageUtility class.
     * <p>
     * The getFileExtension(String filename) method is responsible for extracting the extension
     * from a given filename. If the filename has no extension or an invalid format, it returns
     * an empty string.
     */

    @Test
    void testGetFileExtensionWithValidExtension() {
        // Test filename with valid extension.
        String filename = "example.png";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("png", extension, "The file extension should be 'png'.");
    }

    @Test
    void testGetFileExtensionWithUppercaseExtension() {
        // Test filename with uppercase extension.
        String filename = "example.JPEG";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("jpeg", extension, "The file extension should be in lowercase 'jpeg'.");
    }

    @Test
    void testGetFileExtensionWithMultipleDots() {
        // Test filename with multiple dots in the name.
        String filename = "archive.tar.gz";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("gz", extension, "The file extension should be 'gz'.");
    }

    @Test
    void testGetFileExtensionWithoutExtension() {
        // Test filename without an extension.
        String filename = "filewithoutdot";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("", extension, "The file extension should be an empty string for filenames without a dot.");
    }

    @Test
    void testGetFileExtensionWithDotAtStart() {
        // Test filename starting with a dot (e.g., hidden files on some systems).
        String filename = ".hiddenfile";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("", extension, "The file extension should be an empty string for filenames starting with a dot but having no proper extension.");
    }

    @Test
    void testGetFileExtensionWithDotAtEnd() {
        // Test filename that ends with a dot.
        String filename = "filename.";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("", extension, "The file extension should be an empty string for filenames ending with a dot.");
    }

    @Test
    void testGetFileExtensionWithEmptyString() {
        // Test empty filename.
        String filename = "";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("", extension, "The file extension should be an empty string for an empty filename.");
    }

    @Test
    void testGetFileExtensionWithOnlyDot() {
        // Test filename that only consists of a dot.
        String filename = ".";
        String extension = ImageUtility.getFileExtension(filename);
        assertEquals("", extension, "The file extension should be an empty string for a filename consisting of only a dot.");
    }

    /**
     * Unit tests for the loadFromFile method in ImageUtility class.
     * <p>
     * The loadFromFile(String path) method is responsible for loading an image from a file path.
     * It should handle valid paths, non-existent files, and corrupt images gracefully.
     */


    @Test
    void testLoadFromFileWithValidPath() throws IOException {

        // Create a valid image file for testing.
        BufferedImage sampleImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        File validImageFile = new File("src/test/resources/valid_image.jpg");
        ImageIO.write(sampleImage, "jpg", validImageFile);

        // Test loading a valid image file.
        EditableImage image = ImageUtility.loadFromFile(validImageFile.getAbsolutePath());
        assertEquals(1, image.getWidth(), "The image width should be 1 pixel.");
        assertEquals(1, image.getHeight(), "The image height should be 1 pixel.");
    }

    @Test
    void testLoadFromFileWithNonExistentPath() {
        // Test loading from a non-existent file path.
        String invalidPath = "src/test/resources/non_existent_file.jpg";
        IOException exception = assertThrows(IOException.class, () -> ImageUtility.loadFromFile(invalidPath));

        assertTrue(exception.getMessage().contains("Image file could not be loaded"), "The error message must indicate loading failure.");
    }

    @Test
    void testLoadFromFileWithCorruptImage() {
        // Test loading a corrupt image file.
        File corruptImageFile = new File("src/test/resources/corrupt_image.jpg");
        IOException exception = assertThrows(IOException.class, () -> ImageUtility.loadFromFile(corruptImageFile.getAbsolutePath()));
        assertTrue(exception.getMessage().contains("Image file could not be loaded"), "The error message must indicate loading failure.");
    }

}