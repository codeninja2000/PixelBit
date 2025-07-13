package com.pixelbit;

import com.pixelbit.command.BrightnessCommand;
import com.pixelbit.model.EditableImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class BrightnessCommandTest {
    @Test
    @DisplayName("Apply brightness to an image")
    void testBrightnessCommand() {
        // Create a 1x1 image with a known color (e.g., RGB 100,100,100)
       BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
       Color initialColor = new Color(100, 100, 100);
       image.setRGB(0, 0, initialColor.getRGB());
       EditableImage editableImage = new EditableImage(image);
        // Apply brightness command (+50)
       BrightnessCommand command = new BrightnessCommand(editableImage, 1.5f);
       command.execute();
        // Get the new pixel value
       Color resultColor = new Color(editableImage.getRGB(0, 0));

        // Assert that brightness increased (should be 150,150,150)
        assertEquals(150, resultColor.getRed(), "red pixel");
        assertEquals(150, resultColor.getGreen(), "green pixel");
        assertEquals(150, resultColor.getBlue(), "blue pixel");
    }

    }