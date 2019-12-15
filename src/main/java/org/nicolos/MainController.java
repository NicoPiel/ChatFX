package org.nicolos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import org.nicolos.client.Client;
import org.nicolos.utils.Encrypter;



public class MainController {
      App app = new App();
      @FXML
      private TextField txt_ip;
      @FXML
      private TextField txt_pw;
      @FXML
      private Label logedInUser;
      @FXML
      private Label logedInID;

      private String username;
      private int uid;


      public void Init(int _uid, String _username) {
            this.username = _username;
            this.uid = _uid;

            this.logedInID.setText(Integer.toString(this.uid));
            this.logedInUser.setText(this.username);

      }

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
