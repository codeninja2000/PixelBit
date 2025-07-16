package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrightnessCommandTest {
//    @Test
//    @DisplayName("Apply brightness to an image")
//    void testBrightnessCommand() {
//        // Create a 1x1 image with a known color (e.g., RGB 100,100,100)
//       BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
//       Color initialColor = new Color(100, 100, 100);
//       image.setRGB(0, 0, initialColor.getRGB());
//       EditableImage editableImage = new EditableImage(image);
//        // Apply brightness command (+50)
//       BrightnessCommand command = new BrightnessCommand(editableImage, 1.5f);
//       command.execute();
//        // Get the new pixel value
//       Color resultColor = new Color(editableImage.getRGB(0, 0));
//
//        // Assert that brightness increased (should be 150,150,150)
//        assertEquals(150, resultColor.getRed(), "red pixel");
//        assertEquals(150, resultColor.getGreen(), "green pixel");
//        assertEquals(150, resultColor.getBlue(), "blue pixel");
//    }

    @Test
    @DisplayName("Apply brightness with an invalid factor (negative)")
    void testBrightnessCommandWithNegativeFactor() throws CommandExecException {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Color initialColor = new Color(100, 100, 100);
        image.setRGB(0, 0, initialColor.getRGB());
        EditableImage editableImage = new EditableImage(image);

        BrightnessCommand command = new BrightnessCommand(editableImage, -1.0f);

        command.execute();

        Color resultColor = new Color(editableImage.getRGB(0, 0));

        assertEquals(0, resultColor.getRed());
        assertEquals(0, resultColor.getGreen());
        assertEquals(0, resultColor.getBlue());
    }

    @Test
    @DisplayName("Throws exception when image is null")
    void testBrightnessCommandWithNullImage() {
        EditableImage editableImage = null;

        BrightnessCommand command = new BrightnessCommand(editableImage, 1.0f);

        assertThrows(CommandExecException.class, command::execute);
    }
    
    @Test
    @DisplayName("Brightness factor of 0 should result in black image")
    void testBrightnessCommandWithZeroFactor() throws CommandExecException {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Color initialColor = new Color(100, 100, 100);
        image.setRGB(0, 0, initialColor.getRGB());
        EditableImage editableImage = new EditableImage(image);

        BrightnessCommand commandZero = new BrightnessCommand(editableImage, 0f);
        commandZero.execute();
        
        Color resultColor = new Color(editableImage.getRGB(0, 0));
        assertEquals(0, resultColor.getRed());
        assertEquals(0, resultColor.getGreen());
        assertEquals(0, resultColor.getBlue());
    }

    @Test
    @DisplayName("Brightness factor of 2 should double color values")
    void testBrightnessCommandWithDoubleIntensity() throws CommandExecException {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Color initialColor = new Color(100, 100, 100);
        image.setRGB(0, 0, initialColor.getRGB());
        EditableImage editableImage = new EditableImage(image);

        BrightnessCommand commandTwo = new BrightnessCommand(editableImage, 2f);
        commandTwo.execute();
        
        Color resultColor = new Color(editableImage.getRGB(0, 0));
        assertEquals(200, resultColor.getRed());
        assertEquals(200, resultColor.getGreen());
        assertEquals(200, resultColor.getBlue());
    }
}