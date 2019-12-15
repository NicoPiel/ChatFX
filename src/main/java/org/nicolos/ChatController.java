package org.nicolos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.nicolos.client.Client;

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
                  lbl_username.setText(username);

      }
}
