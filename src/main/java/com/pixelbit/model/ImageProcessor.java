package com.pixelbit.model;

import com.pixelbit.model.filter.Filter;
import java.awt.image.BufferedImage;

public class ImageProcessor {
    
    /**
     * Applies the given filter to the image.
     * 
     * @param image The source image to apply the filter to
     * @param filter The pre-configured filter to apply
     * @return The filtered image
     * @throws IllegalArgumentException if image or filter is null
     */
    public BufferedImage applyFilter(BufferedImage image, Filter filter) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }
        
        return filter.apply(image);
    }
}