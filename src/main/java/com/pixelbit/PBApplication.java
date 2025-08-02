package com.pixelbit;

import com.pixelbit.model.PBModel;
import com.pixelbit.view.PBImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the PixelBit Image Editor.
 * Initializes the model, view, and controller, and sets up the primary stage.
 * This class extends the JavaFX Application class.
 */
public class PBApplication extends Application {

    public static final String ICON_IMAGE_PATH = "/images/pixelbit_title.png";

    @Override
    public void start(Stage stage) throws IOException {
        //  Create model instance
        PBModel model = new PBModel();

        // Create and initialize the view
        PBImageView view = new PBImageView();

           // Initialize the PBController with the above dependencies
        PBController controller = new PBController(model, view);

        // Create icon
        Image icon = new Image(getClass().getResourceAsStream(ICON_IMAGE_PATH));
        stage.getIcons().add(icon);
        // Final setup
        stage.setTitle("PixelBit Image Editor");
        Scene scene = new Scene(view, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}