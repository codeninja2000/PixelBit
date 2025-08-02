package com.pixelbit.model.filter;

import com.pixelbit.exception.InvalidFilterParamsException;
import com.pixelbit.model.filters.*;

import java.util.Map;


/**
 * Factory class for creating filter instances based on the specified type and parameters.
 */
public class FilterFactory {
    /**
     * Creates a filter based on the specified type and parameters.
     *
     * @param type   The type of filter to create.
     * @param params Parameters for the filter as a Map
     * @return An instance of the specified filter type.
     * @throws InvalidFilterParamsException if the provided parameters are invalid.
     */
    public Filter createFilter(FilterType type, Map<String, Object> params) {
        return switch (type) {
            case GRAYSCALE -> new GrayscaleFilter();
            case CONTRAST -> createContrastFilter(params);
            case BRIGHTNESS -> createBrightnessFilter(params);
            case SEPIA -> new SepiaFilter();
            case INVERT -> new InvertFilter();
            case CROP -> createCropFilter(params);
        };
    }

    /**
     * Creates a brightness filter with the specified parameters.
     * If no brightness parameter is provided, a default value of 0 is used.
     *
     * @param params Parameters for the brightness filter as a Map
     * @return An instance of BrightnessFilter with the specified brightness value.
     */
    private Filter createBrightnessFilter(Map<String, Object> params) {
        if (params == null || !params.containsKey("brightness")) {
            return new BrightnessFilter(0); // default value
        }
        try {
            int brightness = ((Number) params.get("brightness")).intValue();
            return new BrightnessFilter(brightness);
        } catch (ClassCastException e) {
            throw new InvalidFilterParamsException("Invalid brightness parameter");
        }
    }

    /**
     * Creates a contrast filter with the specified parameters.
     * If no contrast parameter is provided, a default value of 1.0 is used.
     *
     * @param params Parameters for the contrast filter as a Map
     * @return An instance of ContrastFilter with the specified contrast value.
     */
    private Filter createContrastFilter(Map<String, Object> params) {
        if (params == null || !params.containsKey("contrast")) {
            return new ContrastFilter(1.0); // default value
        }
        try {
            double contrast = ((Number) params.get("contrast")).doubleValue();
            return new ContrastFilter(contrast);
        } catch (ClassCastException e) {
            throw new InvalidFilterParamsException("Invalid contrast parameter");
        }
    }

    /**
     * Creates a crop filter with the specified parameters.
     * The parameters must include x, y, width, and height.
     *
     * @param params Parameters for the crop filter as a Map
     * @return An instance of CropFilter with the specified cropping dimensions.
     * @throws InvalidFilterParamsException if the required parameters are missing or invalid.
     */
    private Filter createCropFilter(Map<String, Object> params) {
        if (params == null || !params.containsKey("x") || !params.containsKey("y") 
            || !params.containsKey("width") || !params.containsKey("height")) {
            throw new InvalidFilterParamsException("Crop filter requires x, y, width, and height parameters");
        }
        try {
            int x = ((Number) params.get("x")).intValue();
            int y = ((Number) params.get("y")).intValue();
            int width = ((Number) params.get("width")).intValue();
            int height = ((Number) params.get("height")).intValue();
            return new CropFilter(x, y, width, height);
        } catch (ClassCastException e) {
            throw new InvalidFilterParamsException("Invalid crop parameters");
        }
    }
}