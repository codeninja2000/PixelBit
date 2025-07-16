package com.pixelbit.model.filter;

import com.pixelbit.model.filters.*;


public class FilterFactory {

   public Filter createFilter(FilterType type, Object... params) {
       return switch (type) {
           case GRAYSCALE -> new GrayscaleFilter();
           case CONTRAST -> createContrastFilter(params);
           case BRIGHTNESS -> createBrightnessFilter(params);
       }
   }

    private Filter createBrightnessFilter(Object[] params) {
       if (params.length == 0) {
           return new BrightnessFilter();
       }
    }

    private Filter createContrastFilter(Object[] params) {
    }
}
