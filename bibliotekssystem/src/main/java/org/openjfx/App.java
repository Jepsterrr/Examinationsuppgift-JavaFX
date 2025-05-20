package org.openjfx;

import java.io.IOException;
import java.sql.SQLException;

import org.openjfx.table.User;
import org.openjfx.util.DBConnection;

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
    private static String currentSceneName;
    private static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {

         try {
            DBConnection.getConnection().close(); // Testar att hämta och returnera en anslutning för att initiera poolen
            System.out.println("Anslutningspool verkar vara initialiserad.");
        } catch (SQLException e) {
            System.err.println("Kunde inte initialisera/testa anslutningspoolen vid start: " + e.getMessage());
            // Hantera detta fel - kanske visa en dialog och avsluta appen om DB är kritisk.
            // Du kan välja att låta felet kastas vidare om ditt statiska block i DBConnection redan gör det.
        }


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
        currentSceneName = fxmlName;
    }

    public static String getCurrentSceneName() {
        if (currentSceneName != null) {
            return currentSceneName;
        }
        return "";
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

     // **** LÄGG TILL DENNA METOD ****
    @Override
    public void stop() throws Exception {
        System.out.println("Applikationen avslutas, stänger anslutningspoolen...");
        DBConnection.closePool(); // Anropar din statiska metod i DBConnection
        super.stop(); // Viktigt att anropa superklassens stop-metod
    }

    public static void main(String[] args) {
        launch();
    }

}   