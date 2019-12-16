package org.nicolos;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nicolos.client.Client;

import java.io.IOException;
import javafx.util.Duration;
import java.util.ArrayList;

public class ChatController {
      private int uid;
      private String username;
      private Client client;

      @FXML
      private Label lbl_username;
      @FXML
      private TextField txt_message;
      @FXML
      private Label tb_chat;

      public void Init(String _username, int _uid, Client c) {
            client = c;
            username =_username;
            uid = _uid;

            if(client.HasSocket())
                  lbl_username.setText(username+" "+uid);

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(client.GetInterval()),
                    ae -> {printInTextArea();})
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
      }

      public void clickedOnQuit(ActionEvent e)  {
           try {
                 client.TerminateConnectionManually();
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

      public void printInTextArea() {
            ArrayList<String> chat = client.GetFullChat();
            StringBuilder sb = new StringBuilder();

            for (String s : chat) {
                  sb.append(s);
                  sb.append("\n");
            }

            tb_chat.setText("\n" + sb.toString());
      }

      public void clickedOnSend(ActionEvent e) {
            if(txt_message.getText() != null || !txt_message.getText().trim().isEmpty()) {
                  client.RequestConnection(txt_message.getText());
            }

            txt_message.clear();
      }
}
