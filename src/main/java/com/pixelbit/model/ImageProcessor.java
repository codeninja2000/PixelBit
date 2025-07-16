package com.pixelbit.model;

import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.model.filter.FilterType;
import java.awt.image.BufferedImage;

public class ImageProcessor {
    private final FilterFactory filterFactory;

    public ImageProcessor(FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }
    public BufferedImage applyFIlter(BufferedImage image, FilterType type) {
        Filter filter = filterFactory.createFilter(type);
        return filter.apply(image);
    }
}
