package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filters.CropFilter;

public class CropCommand extends AbstractPBCommand {

    int x; // X coordinate of the top-left corner of the crop rectangle
    int y; // Y coordinate of the top-left corner of the crop rectangle
    int width; // Width of the crop rectangle
    int height; // Height of the crop rectangle

    /**
     * Constructs a new CropCommand.
     * This command is used to crop the current image.
     * The crop rectangle is defined by the top-left corner (x, y) and its dimensions (width, height).
     * * @param x The x-coordinate of the top-left corner of the crop rectangle.
     *
     * @param y The y-coordinate of the top-left corner of the crop rectangle.
     *          * @param width The width of the crop rectangle.
     *          * @param height The height of the crop rectangle.
     *          * @param image The image to be cropped.
     *          * @param imageService The service to handle image operations.
     *          * @throws IllegalArgumentException if width or height is less than or equal to zero,
     */
    public CropCommand(int x, int y, int width, int height,
                       EditableImage image, ImageService imageService) throws IllegalArgumentException {
        super(image, imageService);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("X and Y coordinates must be non-negative.");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new CropCommand with specified width and height.
     * This constructor is used when the crop rectangle is defined by width and height only.
     * The top-left corner of the crop rectangle is assumed to be at (0, 0).
     *
     * @param width        The width of the crop rectangle.
     * @param height       The height of the crop rectangle.
     * @param image        The image to be cropped.
     * @param imageService The service to handle image operations.
     */
    public CropCommand(int width, int height, EditableImage image, ImageService imageService) {
        this(0, 0, width, height, image, imageService);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("X and Y coordinates must be non-negative.");
        }
        if (x + width > image.getWidth() || y + height > image.getHeight()) {
            throw new IllegalArgumentException("Crop rectangle extends beyond image bounds.");
        }
    }

    /**
     * Executes the crop command.
     *
     * @throws CommandExecException
     */
    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            Filter filter = new CropFilter(x, y, width, height);
        } catch (Exception e) {
            throw new CommandExecException("Error executing crop command: " + e.getMessage(), e);
        }
    }
}
