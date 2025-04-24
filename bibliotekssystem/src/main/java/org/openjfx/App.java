package org.openjfx;

import java.io.IOException;

import org.openjfx.table.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainView"), 820, 640);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static void setRoot(String fxmlName) throws IOException {
        scene.setRoot(loadFXML(fxmlName));
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static void main(String[] args) {
        launch();
    }

}