package org.nicolos;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HostController {

      @FXML
      private TextField txt_roomName;
      @FXML
      private TextField txt_password;
      @FXML
      private Spinner spn_slots;

      public void submit(ActionEvent e) {
            if (txt_roomName.getText() == null || txt_roomName.getText().trim().isEmpty()) {
                  this.showSubmitAlert();
            }
      }

      private void showSubmitAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Die eingegebene Daten sind ungültig.");
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe.");
            alert.showAndWait();
      }

}
