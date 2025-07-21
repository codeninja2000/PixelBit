module com.codeninja.pixelbit {
    requires javafx.controls;
    requires javafx.fxml;
    requires ij;
    requires java.desktop;
    requires javafx.swing;


    opens com.pixelbit to javafx.fxml;
    exports com.pixelbit;
    exports com.pixelbit.util;
    opens com.pixelbit.util to javafx.fxml;
    exports com.pixelbit.command;
    opens com.pixelbit.command to javafx.fxml;
    exports com.pixelbit.model;
    opens com.pixelbit.model to javafx.fxml;
    exports com.pixelbit.exception;
    opens com.pixelbit.exception to javafx.fxml;
    exports com.pixelbit.view;
    opens com.pixelbit.view to javafx.fxml;
}