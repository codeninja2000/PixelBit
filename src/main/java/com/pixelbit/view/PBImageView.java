package com.pixelbit.view;

import com.pixelbit.model.EditableImage;
import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * PBImageView will contain GUI logic for displaying images.
 */
public class PBImageView extends BorderPane {
    private final ImageView imageView = new ImageView();
    private final Label errorLabel = new Label();

    // Menu items
    private final MenuItem undoMenuItem = new MenuItem("Undo");
    private final MenuItem redoMenuItem = new MenuItem("Redo");
    private final MenuItem saveMenuItem = new MenuItem("Save");

    // Filter toolbar buttons
    private final Button grayscaleButton = new Button("Grayscale");
    private final Button invertButton = new Button("Invert");
    private final Button sepiaButton = new Button("Sepia");

    // Adjustment controls
    private final Slider brightnessSlider = new Slider(-1, 1, 0);
    private final Slider contrastSlider = new Slider(-1, 1, 0);


    public PBImageView() {

        errorLabel.setVisible(false);

        // Configure ImageView
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        // Create MenuBar
        MenuBar menuBar = createMenuBar();

        // Create ToolBar for filters
        ToolBar filterToolbar = createFilterToolbar();

        // Create adjustment panel
        VBox adjustmentPanel = createAdjustmentPanel();

        // Set up the layout
        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(menuBar, filterToolbar);

        setTop(topContainer);
        setCenter(imageView);
        setRight(adjustmentPanel);
        setBottom(errorLabel);
        setStyle("-fx-padding: 10;");

    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openItem, saveMenuItem, exitItem);


        // Edit menu
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem);


        menuBar.getMenus().addAll(fileMenu, editMenu);
        return menuBar;
    }

    private ToolBar createFilterToolbar() {
        ToolBar filterToolbar = new ToolBar();
        filterToolbar.getItems().addAll(new Label("Filters: "), grayscaleButton, invertButton, sepiaButton);
        return filterToolbar;

    }

    private VBox createAdjustmentPanel() {
        VBox adjustmentPanel = new VBox(10);
        adjustmentPanel.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");

        adjustmentPanel.getChildren().addAll(
                new Label("Adjustments"),
                new Label("Brightness: "),
                brightnessSlider,
                new Label("Contrast: "),
                contrastSlider
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
     * Updates the ImageView with a new EditableImage.
     *
     * @param editableImage the EditableImage to display
     */
    public void updateImage(EditableImage editableImage) {
        if (editableImage != null && editableImage.getBufferedImage() != null) {
            // Convert EditableImage to JavaFX Image
            Image javafxImage = editableImage.toJavaFXImage();
            imageView.setImage(javafxImage);
        } else {
            imageView.setImage(null);
            showError("No image to display.");
        }
    }

    public void showError(String message) {
        errorLabel.setText("Error: " + message);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        errorLabel.setVisible(true);

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(5));
        pause.setOnFinished(event -> errorLabel.setVisible(false));
        pause.play();
    }

    public void setUndoEnabled(boolean enabled) {
        undoMenuItem.setDisable(!enabled);
    }

    public void setRedoEnabled(boolean enabled) {
        redoMenuItem.setDisable(!enabled);
    }



}
