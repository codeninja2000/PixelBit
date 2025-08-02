package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.model.filter.FilterType;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Command to apply a filter to an image.
 * This command encapsulates the logic for applying a specific filter type
 * with given parameters to an editable image.
 */
public class ApplyFilterCommand extends AbstractPBCommand implements ImageUpdateCommand {


    private final FilterFactory filterFactory; // Factory to create filters

    private final FilterType filterType; // Type of filter to apply
    private final Map<String, Object> parameters; // Parameters for the filter


    /**
     * Constructs a command to apply a filter to an image.
     *
     * @param image         The editable image to which the filter will be applied.
     * @param filterFactory The factory used to create the filter.
     * @param filterType    The type of filter to apply.
     * @param parameters    Parameters for the filter, can be null.
     */
    public ApplyFilterCommand(EditableImage image,
                              FilterFactory filterFactory,
                              FilterType filterType,
                              Map<String, Object> parameters) {
        super(image);
        this.filterFactory = filterFactory;
        this.filterType = filterType;
        this.parameters = parameters != null ? new HashMap<>(parameters) : new HashMap<>();
    }


    /**
     * Executes the command to apply the specified filter to the image.
     * @throws CommandExecException if an error occurs during filter application.
     */
    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            Filter filter = filterFactory.createFilter(filterType, parameters);
            BufferedImage filtered = filter.apply(editableImage.getBufferedImage());
            editableImage.setImage(filtered);
        } catch (Exception e) {
            throw new CommandExecException("Failed to apply filter: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a string representation of the command.
     * @return A string describing the command.
     */
    @Override
public String toString() {
    return "Apply " + filterType + " filter";
}


    /**
     * Returns the updated image after applying the filter.
     * @return The editable image with the applied filter.
     */
    @Override
    public EditableImage getUpdatedImage() {
        return editableImage;
    }
}