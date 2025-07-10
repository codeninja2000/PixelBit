module com.codeninja.pixelbit {
    requires javafx.controls;
    requires javafx.fxml;
    requires ij;
    requires java.desktop;


    opens com.codeninja2000.pixelbit to javafx.fxml;
    exports com.codeninja2000.pixelbit;
}