package org.nicolos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
      App app = new App();
      @FXML
      private TextField txt_ip;
      @FXML
      private TextField txt_pw;
      @FXML
      private AnchorPane APMain;
      @FXML
      private Button btn_test;

      public void joinStuff(ActionEvent e){
            if (txt_pw.getText() == null || txt_pw.getText().trim().isEmpty()) {
                  this.showPasswordAlert();
            }
      }

      public void hostStuff(ActionEvent e) throws IOException {
            Scene sc = new Scene(app.loadFXML("hostserver"));
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stageTheEventSourceNodeBelongs.setScene(sc);
      }

      private void showHostAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Die eingegebene Host-Adresse ist ungültig.");
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe");
            alert.showAndWait();
      }

      private void showPasswordAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Das eingebene Passwort ist falsch");
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe");
            alert.showAndWait();
      }
}
