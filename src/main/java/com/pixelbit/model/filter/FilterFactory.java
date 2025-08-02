package com.pixelbit.model.filter;

import com.pixelbit.exception.InvalidFilterParamsException;
import com.pixelbit.model.filters.*;


/**
 * FilterFactory is responsible for creating instances of different filters.
 * It uses the FilterType enum to determine which filter to create.
 * It also allows for parameters to be passed to the filter constructors,
 * such as brightness and contrast levels.
 */
public class FilterFactory {

    /**
     * Creates a filter based on the specified type and parameters.
     *
     * @param type   The type of filter to create.
     * @param params Optional parameters for the filter, such as brightness or contrast levels.
     * @return An instance of the specified filter type.
     * @throws InvalidFilterParamsException if the provided parameters are invalid for the specified filter type.
     */
    public Filter createFilter(FilterType type, Object... params) {
        return switch (type) {
            case FilterType.GRAYSCALE -> new GrayscaleFilter();
            case FilterType.CONTRAST -> createContrastFilter(params);
            case FilterType.BRIGHTNESS -> createBrightnessFilter(params);
            case FilterType.SEPIA -> new SepiaFilter();
            case FilterType.INVERT -> new InvertFilter();
        };
    }

    /**
     * Creates a BrightnessFilter with the specified parameters.
     *
     * @param params Optional parameters for the BrightnessFilter. Currently only supports a double value for brightness level.
     * @return An instance of BrightnessFilter.
     */
    private Filter createBrightnessFilter(Object[] params) {
        if (params.length == 0) {
            return new BrightnessFilter(0);
        }
        if (params.length == 1 && params[0] instanceof Integer) {
            return new BrightnessFilter((Integer) params[0]);
        }
        throw new InvalidFilterParamsException("Invalid parameters for BrightnessFilter");
    }

    /**
     * Creates a ContrastFilter with the specified parameters.
     *
     * @param params Optional parameters for the ContrastFilter. Currently only supports a double value for contrast factor.
     * @return An instance of ContrastFilter.
     */
    private Filter createContrastFilter(Object[] params) {
        if (params.length == 0) {
            return new ContrastFilter(1.0); // Default contrast factor
        }
        if (params.length == 1 && params[0] instanceof Double) {
            return new ContrastFilter((Double) params[0]);
        }
        throw new InvalidFilterParamsException("Invalid parameters for ContrastFilter");
    }
}
