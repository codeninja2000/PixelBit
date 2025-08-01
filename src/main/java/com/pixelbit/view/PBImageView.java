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


/**
 * PBImageView will contain GUI logic for displaying images.
 */
public class PBImageView extends BorderPane {
   
    // Add a new VBox for the main image view
    //private final VBox mainImagePane = new VBox();
    private final StackPane mainImagePane = new StackPane(); // StackPane to hold the main image view
    private final ImageView mainImageView = new ImageView(); // ImageView to display the main image
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
    // Path to the default image (in your project's resources folder)
    private final Image defaultImage = new Image(getClass().getResource("/images/dark-checkered-bg.jpg").toString());

    public PBImageView() {
        System.out.println(getClass().getResource("/images/placeholder.png"));
        errorLabel.setVisible(false);

        // Initialize with the default image
        mainImageView.setImage(defaultImage);
//        mainImagePane.setVisible(true);
//        mainImagePane.setManaged(true);


        // Configure ImageView for the main image pane
//        mainImageView.setPreserveRatio(true);
        mainImageView.setFitWidth(800);  // Default width
        mainImageView.setFitHeight(600); // Default height

        // Style the mainImagePane
        mainImagePane.setStyle("-fx-background-color: white; -fx-padding: 10;");

        mainImagePane.getChildren().add(mainImageView);
        mainImagePane.setId("mainImagePane");

        // Add the mainImagePane to the layout (e.g., center of the BorderPane)
        setCenter(mainImagePane);

        // Create and set up existing UI components
        MenuBar menuBar = createMenuBar();
        ToolBar filterToolbar = createFilterToolbar();
        VBox adjustmentPanel = createAdjustmentPanel();

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

    // Add method to update status
    public void showStatus(String message) {
        statusLabel.setText(message);

        // Auto-clear after 3 seconds
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(5));
        pause.setOnFinished(event -> statusLabel.setText(""));
        pause.play();
    }


    public MenuItem getExitItem() {
        return exitItem;
    }

    public MenuItem getOpenItem() {
        return openItem;
    }

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
        //System.out.println("EditableImage empty? " + editableImage.isEmpty());
        if (editableImage == null || editableImage.isEmpty()) {
            // Handle the case where there's no image to display
            mainImageView.setImage(defaultImage);

        } else {
            // Convert EditableImage to a JavaFX Image and display it
            mainImageView.setImage(editableImage.toJavaFXImage());

        }

        mainImageView.setPreserveRatio(true); // Ensure the aspect ratio is preserved
        mainImageView.setFitWidth(800);       // Fixed width
        mainImageView.setFitHeight(600);      // Fixed height

        mainImagePane.setVisible(true);
        mainImagePane.setManaged(true);

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

    // Method to show the main image pane with a given image
    public void showMainImagePane(Image image) {
        mainImageView.setImage(image); // Set image in ImageView
        mainImagePane.setVisible(true);
        mainImagePane.setManaged(true); // Ensure the pane occupies layout space
    }

    // Method to hide the main image pane
    public void hideMainImagePane() {
        mainImagePane.setVisible(false);
        mainImagePane.setManaged(false);
    }


}