package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * SepiaFilter applies a sepia tone effect to an image.
 * The sepia effect is achieved by applying a specific transformation to the RGB values of each pixel.
 */
public class SepiaFilter implements Filter {

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("src/main/resources/dhalia.jpg"));
            if (originalImage != null) {
                // Store the processed image returned from apply()
                BufferedImage processedImage = SepiaFilter.applyf(originalImage);
                // Save the processed image
                ImageIO.write(processedImage, "jpg", new File("C:\\Users\\grsim\\IdeaProjects\\PixelBit\\dhalia-sepia.jpg"));
                System.out.println("Image processed and saved as dhalia_sepia.jpg");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param image The image to which the filter will be applied.
     * @return
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Apply sepia transformation
                int r = clamp((int)(color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189));
                int g = clamp((int)(color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168));
                int b = clamp((int)(color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131));
                int a = color.getAlpha(); // preserve original alpha
                sepiaImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
                return sepiaImage;
    }
    public static BufferedImage applyf(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Apply sepia transformation
                int r = clamp((int)(color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189));
                int g = clamp((int)(color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168));
                int b = clamp((int)(color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131));
                int a = color.getAlpha(); // preserve original alpha
                sepiaImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
                return sepiaImage;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return "Sepia";
    }

    @Override
    public String toString() {
        return String.format("%sFilter", this.getName());
    }


}
