package com.pixelbit.model.filter;

import java.awt.image.BufferedImage;

public interface Filter {
    /**
     * Applies the filter to the given image.
     *
     * @param image The image to which the filter will be applied.
     * @return The filtered image.
     */
    BufferedImage apply(BufferedImage image);

    /**
     * Returns the name of the filter.
     *
     * @return The name of the filter.
     */
    String getName();
}
