module com.codeninja2000.pixelbit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.codeninja2000.pixelbit to javafx.fxml;
    exports com.codeninja2000.pixelbit;
}