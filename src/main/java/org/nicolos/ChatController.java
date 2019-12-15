package org.nicolos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.nicolos.client.Client;

import java.io.IOException;

public class ChatController {
      private int uid;
      private String username;
      private Client client;

      @FXML
      private Label lbl_username;
      public void Init(String _username, int _uid, Client c) {
            client = c;
            username =_username;
            uid = _uid;
            if(client.HasSocket())
                  lbl_username.setText(username+" "+uid);

      }

      public void clickedOnQuit(ActionEvent e)  {
           try {
                 client.TerminateConnection(client.GetSocket());
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("main.fxml"));
                 Parent switchscene = fxmlLoader.load();
                 Scene sc = new Scene(switchscene);
                 MainController maincontroller = fxmlLoader.getController();
                 maincontroller.Init(uid,username);
                 Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                 stageTheEventSourceNodeBelongs.setScene(sc);
           }
           catch (IOException ex) {
                 ex.printStackTrace();
            }
      }
}
