package com.pixelbit.view;

import com.pixelbit.model.EditableImage;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Map;
import java.util.HashMap;

import javafx.util.converter.IntegerStringConverter;

/**
 * PBImageView is a custom JavaFX component that displays an image with various functionalities.
 * It includes a menu bar, filter toolbar, adjustment controls, and crop functionality.
 * The component allows users to load, manipulate, and crop images interactively.
 * This class extends BorderPane to provide a flexible layout for the image view and its controls.
 */
public class PBImageView extends BorderPane {

    private final Button cropButton = new Button("Crop");
    // Update the field declarations at the class level
    private final Button undoButton = new Button("⟲");  // Unicode undo symbol
    private final Button redoButton = new Button("⟳");  // Unicode redo symbol
    private final StackPane mainImagePane;
    private final ImageView mainImageView;
    private final Label placeholderLabel;
    private final Label errorLabel = new Label();
    private final Label statusLabel = new Label();
    // Menu items
    private final MenuItem openItem = new MenuItem("Open");
    private final MenuItem exitItem = new MenuItem("Exit");
    private final MenuItem undoMenuItem = new MenuItem("Undo");
    private final MenuItem redoMenuItem = new MenuItem("Redo");
    private final MenuItem saveMenuItem = new MenuItem("Save");
    // Filter toolbar buttons
    private final Button grayscaleButton = new Button("Grayscale");
    private final Button invertButton = new Button("Invert");
    private final Button sepiaButton = new Button("Sepia");
    // Adjustment controls
    private final Slider brightnessSlider = new Slider(-100, 100, 0);
    private final Slider contrastSlider = new Slider(-1, 1, 0);
    private boolean isInCropMode = false; // Flag to track crop mode state
    private TextField cropXField;
    private TextField cropYField;
    private TextField cropWidthField;
    private TextField cropHeightField;
    private VBox cropControlsPane;

    /**
     * Constructor for PBImageView.
     * Initializes the main image pane, image view, and placeholder label.
     * Sets up the layout with a menu bar, filter toolbar, and adjustment panel.
     * This constructor also configures the main image pane to display a placeholder when no image is loaded.
     */
    public PBImageView() {
        // Initialize components
        mainImagePane = new StackPane();
        mainImageView = new ImageView();
        placeholderLabel = createPlaceholderLabel();

        // Configure main image pane
        mainImagePane.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        mainImagePane.setPrefSize(800, 600);
        mainImagePane.setMinSize(400, 300);

        // Configure image view
        mainImageView.setPreserveRatio(true);
        mainImageView.setSmooth(true);
        mainImageView.setCache(true);


        errorLabel.setVisible(false);

        mainImagePane.setId("mainImagePane");

        // Set up the main image pane with both placeholder and image view
        mainImagePane.getChildren().addAll(placeholderLabel, mainImageView);

        setupResizeListener();

        // Center the main image pane in a larger container with padding
        StackPane centerContainer = new StackPane();
        centerContainer.setStyle("-fx-padding: 20; -fx-background-color: white;");
        centerContainer.getChildren().add(mainImagePane);
        setCenter(centerContainer);

        // Create and set up existing UI components
        MenuBar menuBar = createMenuBar();
        ToolBar filterToolbar = createFilterToolbar();
        VBox adjustmentPanel = createAdjustmentPanel();
        createCropControls();

        // Set up the layout
        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(menuBar, filterToolbar);

        setTop(topContainer);
        setRight(adjustmentPanel);

        statusLabel.setStyle("-fx-padding: 5; -fx-background-color: #f0f0f0");

        HBox bottomContainer = new HBox(10);
        bottomContainer.setAlignment(Pos.CENTER_LEFT);
        bottomContainer.getChildren().addAll(errorLabel, statusLabel);
        setBottom(bottomContainer);
        setStyle("-fx-padding: 10;");

    }

    /**
     * Returns the crop button.
     * This button is used to initiate or apply cropping functionality.
     *
     * @return The crop button
     */
    public Button getCropButton() {
        return cropButton;
    }

    /**
     * Creates a placeholder label to display when no image is loaded.
     * The label provides instructions to the user on how to load an image.
     *
     * @return Label with placeholder text
     */
    private Label createPlaceholderLabel() {
        Label label = new Label("No Image Loaded\nClick File → Open to load an image");
        label.setStyle("-fx-font-size: 16; -fx-text-fill: #666666; -fx-font-weight: bold;");
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);
        return label;
    }

    /**
     * Updates the main image view with the provided EditableImage.
     * If the EditableImage is null or empty, it shows a placeholder.
     * Otherwise, it displays the image and scales it to fit within the pane.
     *
     * @param editableImage The EditableImage to display
     */
    public void updateImage(EditableImage editableImage) {
        if (editableImage == null || editableImage.isEmpty()) {
            mainImageView.setImage(null);
            mainImageView.setVisible(false);
            placeholderLabel.setVisible(true);
        } else {
            Image image = editableImage.toJavaFXImage();
            mainImageView.setImage(image);

            // Calculate the scaling to fit the image within the pane
            double paneWidth = mainImagePane.getWidth();
            double paneHeight = mainImagePane.getHeight();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();

            double scale = Math.min(
                paneWidth / imageWidth,
                paneHeight / imageHeight
            );

            // Apply the scaling
            mainImageView.setFitWidth(imageWidth * scale);
            mainImageView.setFitHeight(imageHeight * scale);

            mainImageView.setVisible(true);
            placeholderLabel.setVisible(false);
        }
    }

    /**
     * Sets up listeners for resizing the main image pane.
     * This method ensures that the image view resizes correctly when the pane size changes.
     */
    private void setupResizeListener() {
        mainImagePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (mainImageView.getImage() != null) {
                updateImageSize();
            }
        });

        mainImagePane.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (mainImageView.getImage() != null) {
                updateImageSize();
            }
        });
    }

    /**
     * Updates the size of the main image view based on the current pane size.
     * This method ensures that the image fits within the pane while maintaining its aspect ratio.
     */
    private void updateImageSize() {
        Image image = mainImageView.getImage();
        if (image != null) {
            double paneWidth = mainImagePane.getWidth();
            double paneHeight = mainImagePane.getHeight();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();

            double scale = Math.min(
                paneWidth / imageWidth,
                paneHeight / imageHeight
            );

            mainImageView.setFitWidth(imageWidth * scale);
            mainImageView.setFitHeight(imageHeight * scale);
        }
    }

    /**
     * Displays a status message to the user.
     * The message will be shown in the status label and will disappear after 5 seconds.
     *
     * @param message The status message to display
     */
    public void showStatus(String message) {
        statusLabel.setText(message);

        // Auto-clear after 3 seconds
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(5));
        pause.setOnFinished(event -> statusLabel.setText(""));
        pause.play();
    }

    /**
     * Returns the exit menu item.
     * @return The exit menu item
     */
    public MenuItem getExitItem() {
        return exitItem;
    }

    /**
     * Returns the open menu item.
     * This item is used to open an image file.
     *
     * @return The open menu item
     */
    public MenuItem getOpenItem() {
        return openItem;
    }

    /**
     * Creates the menu bar with File and Edit menus.
     * The File menu includes options to open, save, and exit.
     * The Edit menu includes undo and redo options.
     *
     * @return MenuBar containing the application menus
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");

        fileMenu.getItems().addAll(openItem, saveMenuItem, exitItem);


        // Edit menu
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem);


        menuBar.getMenus().addAll(fileMenu, editMenu);
        return menuBar;
    }

    /**
     * Creates the filter toolbar with buttons for image filters and undo/redo functionality.
     * The toolbar includes buttons for grayscale, invert, sepia, and crop.
     *
     * @return ToolBar containing filter buttons
     */
    private ToolBar createFilterToolbar() {
        ToolBar filterToolbar = new ToolBar();

        // Style the undo/redo buttons
        undoButton.setTooltip(new Tooltip("Undo (Ctrl+Z)"));
        redoButton.setTooltip(new Tooltip("Redo (Ctrl+Y)"));
        String buttonStyle = "-fx-font-size: 16; -fx-padding: 4 8 4 8;";
        undoButton.setStyle(buttonStyle);
        redoButton.setStyle(buttonStyle);

        // Bind the disable property of the buttons to the menu items
        undoButton.disableProperty().bind(undoMenuItem.disableProperty());
        redoButton.disableProperty().bind(redoMenuItem.disableProperty());

        filterToolbar.getItems().addAll(
            undoButton,
            redoButton,
            new Separator(),
            new Label("Filters: "),
            grayscaleButton,
            invertButton,
            sepiaButton,
            new Separator(),
            cropButton
        );

        return filterToolbar;
    }

    /**
     * Creates the adjustment panel with sliders for brightness and contrast.
     * The panel includes a reset button to revert adjustments.
     *
     * @return VBox containing the adjustment controls
     */
    private VBox createAdjustmentPanel() {
        VBox adjustmentPanel = new VBox(10);
        adjustmentPanel.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");

        Button resetButton = new Button("Reset to Original");
        resetButton.setMaxWidth(Double.MAX_VALUE);

        adjustmentPanel.getChildren().addAll(
                new Label("Adjustments"),
                new Label("Brightness: "),
                brightnessSlider,
                new Label("Contrast: "),
                contrastSlider,
                new Separator(),
                resetButton
        );
        return adjustmentPanel;
    }

    // Getters for menu items
    public MenuItem getUndoMenuItem() { return undoMenuItem; }

    public MenuItem getRedoMenuItem() { return redoMenuItem; }

    public MenuItem getSaveMenuItem() { return saveMenuItem; }

    // Getters for filter buttons
    public Button getGrayscaleButton() { return grayscaleButton; }

    public Button getSepiaButton() { return sepiaButton; }

    public Button getInvertButton() { return invertButton; }

    // Getters for adjustment controls
    public Slider getBrightnessSlider() { return brightnessSlider; }

    public Slider getContrastSlider() { return contrastSlider; }

    /**
     * Returns the reset button from the adjustment panel.
     * This button is used to reset adjustments made to the image.
     *
     * @return The reset button
     */
    public Button getResetButton() {
        return (Button) ((VBox) getRight()).getChildren().get(6); // Get the reset button from the adjustment panel
    }

    /**
     * Displays an error message to the user.
     * The error message will be shown in red and will disappear after 5 seconds.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        errorLabel.setText("Error: " + message);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        errorLabel.setVisible(true);

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(5));
        pause.setOnFinished(event -> errorLabel.setVisible(false));
        pause.play();
    }

    /**
     * Sets the undo menu item enabled or disabled.
     *
     * @param enabled true to enable, false to disable
     */
    public void setUndoEnabled(boolean enabled) {
        undoMenuItem.setDisable(!enabled);
    }

    /**
     * Sets the redo menu item enabled or disabled.
     *
     * @param enabled true to enable, false to disable
     */
    public void setRedoEnabled(boolean enabled) {
        redoMenuItem.setDisable(!enabled);
    }

    /**
     * Creates the crop controls pane with width and height fields.
     * The fields are set to accept only numeric input.
     */
    private void createCropControls() {
        cropControlsPane = new VBox(5);
        cropControlsPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");
        
        cropWidthField = new TextField();
        cropHeightField = new TextField();
        
        // Set numeric-only input for both fields
        cropWidthField.setTextFormatter(createIntegerFormatter());
        cropHeightField.setTextFormatter(createIntegerFormatter());
        
        // Labels and layout
        cropControlsPane.getChildren().addAll(
            new Label("Crop Dimensions"),
            new Label("Width:"), cropWidthField,
            new Label("Height:"), cropHeightField
        );
        
        // Initially hide the crop controls
        cropControlsPane.setVisible(false);
        cropControlsPane.setManaged(false);
    }

    /**
     * Creates a TextFormatter for integer input.
     * This formatter allows only numeric input and sets a default value of 0.
     *
     * @return TextFormatter for integer input
     */
    private TextFormatter<Integer> createIntegerFormatter() {
        return new TextFormatter<>(new IntegerStringConverter(), 0,
            change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) {
                    return change;
                }
                return null;
            });
    }

    /**
     * Returns the crop parameters as a Map.
     * The map contains keys "x", "y", "width", and "height" with their respective values.
     * If the main image is not set, returns null.
     *
     * @return Map with crop parameters or null if no image is set
     */
    public Map<String, Object> getCropParameters() {
        Map<String, Object> params = new HashMap<>();
        Image image = mainImageView.getImage();
        if (image == null) return null;
        
        int width = Integer.parseInt(cropWidthField.getText());
        int height = Integer.parseInt(cropHeightField.getText());
        
        // Calculate center position
        int x = (int)((image.getWidth() - width) / 2);
        int y = (int)((image.getHeight() - height) / 2);
        
        params.put("x", x);
        params.put("y", y);
        params.put("width", width);
        params.put("height", height);
        return params;
    }

    /**
     * Initializes the crop mode by showing crop controls and setting initial values.
     */
    public void initCropMode() {
        isInCropMode = true;
        
        // Show the crop controls pane
        cropControlsPane.setVisible(true);
        cropControlsPane.setManaged(true);
        
        // Set initial values based on image dimensions
        Image image = mainImageView.getImage();
        if (image != null) {
            cropWidthField.setText(String.valueOf((int)image.getWidth()));
            cropHeightField.setText(String.valueOf((int)image.getHeight()));
        }
        
        // Add the crop controls to the right side
        setRight(cropControlsPane);
        
        // Change the crop button text
        cropButton.setText("Apply Crop");
    }

    public void exitCropMode() {
        isInCropMode = false;
        cropControlsPane.setVisible(false);
        cropControlsPane.setManaged(false);
        setRight(createAdjustmentPanel());
        cropButton.setText("Start Crop");
    }

    /**
     * Validates the crop parameters entered by the user.
     * @return true if the parameters are valid, false otherwise
     */
    public boolean validateCropParameters() {
        try {
            int width = Integer.parseInt(cropWidthField.getText());
            int height = Integer.parseInt(cropHeightField.getText());
            Image image = mainImageView.getImage();
            
            if (width <= 0 || height <= 0) {
                showError("Width and height must be positive numbers");
                return false;
            }
            
            if (width > image.getWidth() || height > image.getHeight()) {
                showError("Crop dimensions cannot be larger than image dimensions");
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for width and height");
            return false;
        }
    }

    /**
     * Returns the undo button.
     * @return undoButton
     */
    public Button getUndoButton() {
    return undoButton;
}

    /**
     * Returns the redo button.
     * @return redoButton
     */
public Button getRedoButton() {
    return redoButton;
}
}