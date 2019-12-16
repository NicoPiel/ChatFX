package org.nicolos;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nicolos.client.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class ChatController {
      private int uid;
      private String username;
      private Client client;

      @FXML
      private Label lbl_username;
      @FXML
      private TextField txt_message;
      @FXML
      private TextArea tb_chat;

      public void Init(String _username, int _uid, Client c) {
            client = c;
            username =_username;
            uid = _uid;

            if(client.HasSocket())
                  lbl_username.setText(username+" "+uid);

            new Timer().schedule(
                    new TimerTask() {
                          @Override
                          public void run() {
                                printInTextArea();
                          }
                    }, 0, 3000);
      }

      public void clickedOnQuit(ActionEvent e)  {
           try {
                 client.TerminateConnection();
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
            }
      }

      public void clickedOnSend(ActionEvent e) {
            if(txt_message.getText() != null || !txt_message.getText().trim().isEmpty()) {
                  client.RequestConnection(txt_message.getText());
            }
            txt_message.clear();
      }
}
