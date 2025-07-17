package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.model.filter.FilterType;

import java.util.HashMap;
import java.util.Map;

// This class will replace all the indi
public class ApplyFilterCommand extends AbstractPBCommand implements ImageUpdateCommand {
    private final Filter filter;
    private final Map<String, Object> parameters;
    private final FilterFactory filterFactory;  // Add this field

    public ApplyFilterCommand(ImageService imageService, FilterFactory filterFactory, EditableImage editableImage,
                              FilterType filterType, Map<String, Object> parameters) {
        super(editableImage, imageService);
        this.filterFactory = filterFactory;
        this.parameters = parameters;
        this.filter = createFilter(filterType, parameters);
    }

    private Filter createFilter(FilterType filterType, Map<String, Object> parameters) {
        if (filterType == null) {
            throw new IllegalArgumentException("FilterType cannot be null");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        
        Object[] paramValues = parameters.values().toArray();
        return filterFactory.createFilter(filterType, paramValues);
    }

    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            imageService.applyFilter(editableImage.getBufferedImage(), filter);
        } catch (Exception e) {
            throw new CommandExecException("Error applying filter: " + e.getMessage(), e);
        }
    }

    @Override
    public EditableImage getUpdatedImage() {
        return editableImage;
    }
}
//
//        // Usage example:
//        Map<String, Object> params = new HashMap<>();
//        params.put("factor", 1.5); // for contrast filter
//        var command = new ApplyFilterCommand(imageService, editableImage, FilterType.CONTRAST, params);