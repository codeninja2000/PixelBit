package com.pixelbit;

import com.pixelbit.model.PBModel;
import com.pixelbit.view.PBImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PBApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Step 1: Create model instance
        PBModel model = new PBModel();

        // Step 2: Create and initialize the view
        PBImageView view = new PBImageView();

           // Step 3: Initialize the PBController with the above dependencies
        PBController controller = new PBController(model, view);

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