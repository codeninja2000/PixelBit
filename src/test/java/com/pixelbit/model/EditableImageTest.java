package com.pixelbit.model;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EditableImage class, specifically testing the deepCopy method.
 * These tests ensure that the deepCopy method creates an independent copy of the EditableImage,
 * preserving its properties and allowing modifications without affecting the original image.
 */
class EditableImageTest {

    /**
     * This class tests the `deepCopy` method in the `EditableImage` class.
     * The `deepCopy` method creates a deep copy of the current `EditableImage` object,
     * ensuring that changes made to the copy do not affect the original object.
     */

    @Test
    void testDeepCopyCreatesIndependentCopy() {
        // Arrange
        BufferedImage originalImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        originalImage.setRGB(2, 2, Color.RED.getRGB());
        EditableImage original = new EditableImage(originalImage, "original", "png");

        // Act
        EditableImage copy = original.deepCopy();
        copy.getBufferedImage().setRGB(2, 2, Color.BLUE.getRGB());

        // Assert
        assertNotEquals(original.getRGB(2, 2), copy.getRGB(2, 2));
        assertEquals(Color.RED.getRGB(), original.getRGB(2, 2));
        assertEquals(Color.BLUE.getRGB(), copy.getRGB(2, 2));
    }

    @Test
    void testDeepCopyPreservesDimensionsAndType() {
        // Arrange
        BufferedImage originalImage = new BufferedImage(7, 10, BufferedImage.TYPE_INT_RGB);
        EditableImage original = new EditableImage(originalImage, "testImage", "jpg");

        // Act
        EditableImage copy = original.deepCopy();

        // Assert
        assertEquals(original.getWidth(), copy.getWidth());
        assertEquals(original.getHeight(), copy.getHeight());
        assertEquals(original.getBufferedImage().getType(), copy.getBufferedImage().getType());
    }

    @Test
    void testDeepCopyPreservesMetaInfo() {
        // Arrange
        BufferedImage originalImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        EditableImage original = new EditableImage(originalImage, "metaTest", "png");

        // Act
        EditableImage copy = original.deepCopy();

        // Assert
        assertEquals(original.getFilename(), copy.getFilename());
        assertEquals(original.getFormat(), copy.getFormat());
    }

    @Test
    void testDeepCopyDoesNotModifyOriginalPixels() {
        // Arrange
        BufferedImage originalImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        originalImage.setRGB(1, 1, Color.GREEN.getRGB());
        EditableImage original = new EditableImage(originalImage);

        // Act
        EditableImage copy = original.deepCopy();
        copy.getBufferedImage().setRGB(1, 1, Color.YELLOW.getRGB());

        // Assert
        assertEquals(Color.GREEN.getRGB(), original.getRGB(1, 1));
        assertEquals(Color.YELLOW.getRGB(), copy.getRGB(1, 1));
    }

    @Test
    void testDeepCopyOnEmptyImage() {
        // Arrange
        EditableImage original = new EditableImage();

        // Act
        EditableImage copy = original.deepCopy();

        // Assert
        assertNotSame(original.getBufferedImage(), copy.getBufferedImage());
        assertTrue(copy.isEmpty());
    }

    @Test
    void testDeepCopyWithLargeImage() {
        // Arrange
        BufferedImage largeImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        largeImage.setRGB(900, 500, Color.ORANGE.getRGB());
        EditableImage original = new EditableImage(largeImage, "largeImage", "bmp");

        // Act
        EditableImage copy = original.deepCopy();

        // Assert
        assertEquals(original.getRGB(900, 500), copy.getRGB(900, 500));
        assertNotSame(original.getBufferedImage(), copy.getBufferedImage());
    }

    @Test
    void testDeepCopyOnImageLoadedFromFile() throws IOException {
        // Arrange
        File tempFile = File.createTempFile("testImage", ".png");
        tempFile.deleteOnExit();
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, Color.MAGENTA.getRGB());
        ImageIO.write(testImage, "png", tempFile);

        EditableImage original = new EditableImage(tempFile.getAbsolutePath());

        // Act
        EditableImage copy = original.deepCopy();

        // Assert
        assertEquals(original.getRGB(0, 0), copy.getRGB(0, 0));
        assertEquals(Color.MAGENTA.getRGB(), copy.getRGB(0, 0));
    }
}