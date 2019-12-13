package org.nicolos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage primarystage) throws IOException {
        this.stage = primarystage;
        this.scene = new Scene(loadFXML("main"));

        this.setupStage();
    }

    private void setupStage() throws IOException {
        this.stage.setScene(this.scene);
        this.stage.getIcons().add(new Image("file:Assets/icon16.png"));
        this.stage.show();
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}