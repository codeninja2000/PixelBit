package com.pixelbit.model.filter;

import com.pixelbit.model.filters.GrayscaleFilter;

public class FilterFactory {

   public Filter createFilter(FilterType type) {
       return switch (type) {
           case GRAYSCALE -> new GrayscaleFilter();
       }
   }
}
