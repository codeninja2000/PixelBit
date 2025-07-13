package com.pixelbit;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PBController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

//    public void saveImage(EditableImage image) {
//        BufferedImage toSave = image.getCurrentImage();
//        ImageIO.write(toSave, "png", new File("output.png"));
//        image.resetModifiedFlag(); // ← mark as not dirty after save
//    }

}