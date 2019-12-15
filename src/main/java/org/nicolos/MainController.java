package org.nicolos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

      public void clickedOnJoin(ActionEvent e){
            System.out.println("Clicked on Join");
            if (txt_ip.getText() == null || txt_ip.getText().trim().isEmpty()) {
                  showHostAlert();
            }else{
                  System.out.println("Ip not null");
                  Client c = new Client(txt_ip.getText());
                  System.out.println(c.HasSocket());
                  if(c.HasSocket()) {
                        System.out.println("Socket not null");
                        try {
                              FXMLLoader fxmlLoader = new FXMLLoader();
                              fxmlLoader.setLocation(getClass().getResource("chatclient.fxml"));
                              Parent switchscene = fxmlLoader.load();
                              Scene sc = new Scene(switchscene);
                              ChatController cc = fxmlLoader.getController();
                              cc.Init(username,uid, c);
                              Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) e.getSource()).getScene().getWindow();
                              stageTheEventSourceNodeBelongs.setScene(sc);
                        }catch(IOException ex){
                              ex.printStackTrace();
                        }
                  }
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
