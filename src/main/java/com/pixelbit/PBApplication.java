package com.pixelbit;

import com.pixelbit.model.ImageService;
import com.pixelbit.model.PBModel;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.view.PBImageView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PBApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PBApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // Step 1: Create model instance
        PBModel model = new PBModel();

// Step 2: Create and initialize the view
        PBImageView view = new PBImageView();

// Step 3: Create the ImageService
        ImageService imageService = new ImageService();

// Step 4: Create the FilterFactory
        FilterFactory filterFactory = new FilterFactory();

// Step 5: Initialize the PBController with the above dependencies
        PBController controller = new PBController(model, view, imageService, filterFactory);

        stage.setTitle("PixelBit Image Editor");
        Scene scene = new Scene(new PBImageView(), 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}