module org.nicolos {
    requires javafx.controls;
    requires javafx.fxml;
      requires java.sql;

      opens org.nicolos to javafx.fxml;
    exports org.nicolos;
}