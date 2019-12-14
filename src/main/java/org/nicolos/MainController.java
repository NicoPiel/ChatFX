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
import org.nicolos.client.Client;
import org.nicolos.utils.Encrypter;

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
      @FXML
      private Label lbl_test;
      @FXML
      private Label lbl_test1;

      public void clickedOnLogin(){
            if (txt_ip.getText() == null || txt_ip.getText().trim().isEmpty()) {
                  showHostAlert();
            }else{
                  Client c = new Client(txt_ip.getText());
            }

            Encrypter e = new Encrypter();
            if (txt_pw.getText() == null || txt_pw.getText().trim().isEmpty()) {
                  showPasswordAlert();
            }else{

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
