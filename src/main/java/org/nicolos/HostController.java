package org.nicolos;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class HostController {

      @FXML
      private TextField txt_roomName;
      @FXML
      private TextField txt_password;
      @FXML
      private Slider sld_slots;
      @FXML
      private Label lbl_slots;

      public void submit(ActionEvent e) {
            if (txt_roomName.getText() == null || txt_roomName.getText().trim().isEmpty()) {
                  this.showSubmitAlert();
            }
      }

      public void slotsSliderListener(Event e) {
             lbl_slots.setText(""+sld_slots.getValue());
      }

      private void showSubmitAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Die eingegebene Daten sind ungültig.");
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe.");
            alert.showAndWait();
      }

}
