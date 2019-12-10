module org.nicolos {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.nicolos to javafx.fxml;
    exports org.nicolos;
}