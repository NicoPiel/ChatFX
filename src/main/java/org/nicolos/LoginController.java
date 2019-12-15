package org.nicolos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nicolos.utils.Encrypter;
import org.nicolos.utils.MySQLManager;

import java.io.IOException;

public class LoginController {
      @FXML
      private TextField txt_username;
      @FXML
      private PasswordField pwb_password;


      public void clickedOnRegister(ActionEvent e) throws IOException {
            App app = new App();
            Scene sc = new Scene(app.loadFXML("register"));
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stageTheEventSourceNodeBelongs.setScene(sc);
      }
      public void clickedOnLogin(ActionEvent e) {
            if(checkAllSet()) {
                  Encrypter ex = new Encrypter();
                  MySQLManager sql = new MySQLManager();
                  String Encryptedpassword = sql.getPassword_Hash(txt_username.getText());
                  String salt = sql.getSalt(txt_username.getText());
                  System.out.println("Login try");
                  if (ex.checkPassword(pwb_password.getText(), salt, Encryptedpassword)) {
                        System.out.println("Login accepted");
                        try{
                              App app = new App();
                              Scene sc = new Scene(app.loadFXML("main"));
                              Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                              stageTheEventSourceNodeBelongs.setScene(sc);
                        }catch(IOException io){
                              io.printStackTrace();
                        }

                  }
            }
      }

      private boolean checkAllSet() {
            if(txt_username.getText()==null || pwb_password.getText() == null){
                  return false;
            }
            if (txt_username.getText().trim().isEmpty() || pwb_password.getText().trim().isEmpty()) {
                  return false;
            }
            return true;
      }
}
