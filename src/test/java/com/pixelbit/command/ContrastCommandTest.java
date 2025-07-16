package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ContrastCommandTest {

    @Test
    void testExecuteAppliesContrastSuccessfully() throws CommandExecException {
        // Arrange
        BufferedImage originalImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        originalImage.setRGB(0, 0, new Color(100, 100, 100).getRGB());
        originalImage.setRGB(0, 1, new Color(150, 150, 150).getRGB());
        originalImage.setRGB(1, 0, new Color(200, 200, 200).getRGB());
        originalImage.setRGB(1, 1, new Color(50, 50, 50).getRGB());
        EditableImage editableImage = new EditableImage(originalImage);

        double contrastFactor = 1.2;
        PBCommand contrastCommand = new ContrastCommand(editableImage, contrastFactor);

        // Act
        contrastCommand.execute();

        // Assert
        BufferedImage adjustedImage = editableImage.getBufferedImage();
        assertNotEquals(originalImage.getRGB(0, 0), adjustedImage.getRGB(0, 0));
        assertNotEquals(originalImage.getRGB(0, 1), adjustedImage.getRGB(0, 1));
        assertNotEquals(originalImage.getRGB(1, 0), adjustedImage.getRGB(1, 0));
        assertNotEquals(originalImage.getRGB(1, 1), adjustedImage.getRGB(1, 1));
    }

    @Test
    void testExecuteThrowsExceptionForNullImage() {
        // Arrange
        EditableImage editableImage = new EditableImage((BufferedImage) null);
        double contrastFactor = 1.5;
        ContrastCommand contrastCommand = new ContrastCommand(editableImage, contrastFactor);

        // Act and Assert
        CommandExecException exception = assertThrows(CommandExecException.class, contrastCommand::execute);
        //assertEquals("Null image provided for contrast adjustment.", exception.getMessage());
        assertTrue(exception.getMessage().contains("Null image provided for contrast adjustment."));
    }

    @Test
    void testExecuteDoesNotModifyImageWhenFactorIsOne() throws CommandExecException {
        // Arrange
        BufferedImage originalImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        originalImage.setRGB(0, 0, new Color(100, 100, 100).getRGB());
        originalImage.setRGB(0, 1, new Color(150, 150, 150).getRGB());
        originalImage.setRGB(1, 0, new Color(200, 200, 200).getRGB());
        originalImage.setRGB(1, 1, new Color(50, 50, 50).getRGB());
        EditableImage editableImage = new EditableImage(originalImage);

        double contrastFactor = 1.0;
        ContrastCommand contrastCommand = new ContrastCommand(editableImage, contrastFactor);

        // Act
        contrastCommand.execute();

        // Assert
        BufferedImage adjustedImage = editableImage.getBufferedImage();
        assertEquals(originalImage.getRGB(0, 0), adjustedImage.getRGB(0, 0));
        assertEquals(originalImage.getRGB(0, 1), adjustedImage.getRGB(0, 1));
        assertEquals(originalImage.getRGB(1, 0), adjustedImage.getRGB(1, 0));
        assertEquals(originalImage.getRGB(1, 1), adjustedImage.getRGB(1, 1));
    }

    @Test
    void testExecuteThrowsExceptionForInvalidFactor() {
        // Arrange
        BufferedImage originalImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        EditableImage editableImage = new EditableImage(originalImage);

        double invalidFactor = -1.0;
        ContrastCommand contrastCommand = new ContrastCommand(editableImage, invalidFactor);

        // Act and Assert
        Exception exception = assertThrows(CommandExecException.class, contrastCommand::execute);
        assertTrue(exception.getMessage().contains("Error applying contrast adjustment"));
    }
}