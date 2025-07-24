package com.pixelbit.model;

import com.pixelbit.model.filter.*;
import java.awt.image.BufferedImage;

public class ImageService {
    private final ImageProcessor imageProcessor;
    private final FilterFactory filterFactory;

    public ImageService(ImageProcessor imageProcessor, FilterFactory filterFactory) {
        this.imageProcessor = imageProcessor;
        this.filterFactory = filterFactory;
    }

    // For pre-configured filters
    public BufferedImage applyFilter(BufferedImage image, Filter filter) {
        return imageProcessor.applyFilter(image, filter);
    }


    // For creating filters with parameters
    public BufferedImage applyFilter(BufferedImage image, FilterType type, Object... params) {
        Filter filter = filterFactory.createFilter(type, params);
        return applyFilter(image, filter);
    }

    // For simple filters without parameters
    public BufferedImage applyFilter(BufferedImage image, FilterType type) {
        return applyFilter(image, type, new Object[0]);
    }
}