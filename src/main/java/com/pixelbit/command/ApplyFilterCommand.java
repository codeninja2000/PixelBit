package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.model.filter.FilterType;

import java.awt.image.BufferedImage;
import java.util.Map;

// This class will replace all the indi
public class ApplyFilterCommand extends AbstractPBCommand implements ImageUpdateCommand {
    private final Filter filter;

    public ApplyFilterCommand(EditableImage editableImage, FilterFactory filterFactory, 
                            FilterType filterType, Map<String, Object> parameters) {
        super(editableImage);
        
        // Simplify parameter handling
        Object[] params = null;
        if (parameters != null && !parameters.isEmpty()) {
            switch (filterType) {
                case BRIGHTNESS -> params = new Object[]{parameters.get("brightness")};
                case CONTRAST -> params = new Object[]{parameters.get("contrast")};
                case SEPIA, GRAYSCALE -> params = new Object[0];
            }
        }
        
        this.filter = filterFactory.createFilter(filterType, params);
    }

    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            BufferedImage filteredImage = filter.apply(editableImage.getBufferedImage());
            editableImage.setImage(filteredImage);
        } catch (Exception e) {
            throw new CommandExecException("Error applying filter: " + e.getMessage(), e);
        }
    }

    @Override
    public EditableImage getUpdatedImage() {
        return editableImage;
    }
}