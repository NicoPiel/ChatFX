package org.nicolos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nicolos.utils.Encrypter;
import org.nicolos.utils.MySQLManager;
import java.util.regex.Pattern;


import java.io.IOException;

public class RegisterController {
      @FXML
      private TextField txt_username;
      @FXML
      private TextField txt_mail;
      @FXML
      private PasswordField pwb_password;
      @FXML
      private PasswordField pwb_verify_password;

      public void clickedOnRegister(ActionEvent e)  {
            System.out.println("Shit");
            if (!this.checkAllSet()) {
                  this.showWrongInputAlert();
                  return;
            }
            System.out.println(this.checkAllSet());
            if (!pwb_password.getText().equals(pwb_verify_password.getText())){
                  showPasswordsDontMatchAlert();
                  return;
            }
            if(!this.checkIfValidEmail(txt_mail.getText())) {
                  this.showFalseEmailAlert();
                  return;
            }

            MySQLManager sql = new MySQLManager();

            if(!sql.isUsernameAvailable(txt_username.getText())) {
                  this.showUsernameNotAvailableAlert();
                  return;
            }

            if(!sql.isEmailAvailable(txt_mail.getText())) {
                  this.showEmailNotAvailableAlert();
                  return;
            }

            Encrypter en = new Encrypter();
            String password[] = en.encryptPassword(pwb_password.getText());
            String Encryptedpassword = password[0];
            String salt = password[1];
            String strsql = "INSERT INTO userlogin(username, email, password_hash, salt1) VALUES ('"+txt_username.getText()+"','"+txt_mail.getText()+"','"+Encryptedpassword+"','"+salt+"');";
            if(sql.executeUpdate(strsql)){
                  System.out.println("Datenbank modifiziert.");
            }
            try{
                  App app = new App ();
                  Scene sc = new Scene(app.loadFXML("register"));
                  Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                  stageTheEventSourceNodeBelongs.setScene(sc);
            }catch(IOException ex) {
                  ex.printStackTrace();
            }

      }
      public void clickedOnBackToLogin(ActionEvent e){
            try{
                  App app = new App ();
                  Scene sc = new Scene(app.loadFXML("login"));
                  Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                  stageTheEventSourceNodeBelongs.setScene(sc);
            }catch(IOException ex) {
                  ex.printStackTrace();
            }

      }
      private boolean checkIfValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                  return false;
            return pat.matcher(email).matches();

      }


      private boolean checkAllSet() {
            if(txt_mail.getText()==null || txt_username.getText() == null || pwb_password.getText() == null || pwb_verify_password.getText()== null){
                  return false;
            }
            if (txt_mail.getText().trim().isEmpty() || txt_username.getText().trim().isEmpty() || pwb_password.getText().trim().isEmpty() || pwb_verify_password.getText().trim().isEmpty()) {
                  return false;
            }
            return true;
      }


      private void showEmailNotAvailableAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("E-Mail-Adresse bereits bekannt");
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Bitte benutze eine andere");
            alert.showAndWait();
      }
      private void showUsernameNotAvailableAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Der Benutzername ist bereits vergeben.");
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Bitte nutze einen anderen.");
            alert.showAndWait();
      }
      private void showFalseEmailAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bitte gebe eine echte E-Mail-Adresse an.");
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe");
            alert.showAndWait();
      }
      private void showWrongInputAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deine Eingegebenen Daten sind unvollständig");
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe");
            alert.showAndWait();
      }

      private void showPasswordsDontMatchAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Die Passwörter stimmen nicht überein");
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Bitte überprüfe deine Eingabe");
            alert.showAndWait();
      }
}
