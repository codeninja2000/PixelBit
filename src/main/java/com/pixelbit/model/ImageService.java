package com.pixelbit.model;

import com.pixelbit.model.filter.FilterType;
import java.awt.image.BufferedImage;

public class ImageService {
    private final ImageProcessor imageProcessor;
    // private final ImageValidator imageValidator;

    public ImageService(ImageProcessor imageProcessor) {
        this.imageProcessor = imageProcessor;
        // this.imageValidator = imageValidator;
    }
    // TODO: Implement image validation if needed
    // TODO: Implement parameter passing for filters if needed
    public BufferedImage applyFilter(BufferedImage image, FilterType type) {
        // Validate the image if needed
        // imageValidator.validate(image);

        // Applu business rules
        // Handle pre/post-processing if needed
        // Apply the filter using the ImageProcessor
        return imageProcessor.applyFIlter(image, type);
    }
}
