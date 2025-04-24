package org.openjfx.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.openjfx.App;
import org.openjfx.dao.UserDAO;
import org.openjfx.table.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInViewController {

    @FXML
    private Label statusLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void btnHome() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void btnLogIn() throws IOException {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("Fyll i användarnamn eller lösenord!");
        }

        try {
            UserDAO userDAO = new UserDAO();
            User foundUser = userDAO.findByUsername(user);
            if (foundUser != null && foundUser.getPassword().equals(pass)) {
                // Inloggning lyckad: byt vy och anropa handleLogIn
                App.setCurrentUser(foundUser);
                App.setRoot("MainView");
            } else {
                statusLabel.setText("Fel användarnamn eller lösenord");
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fel: " + e.getMessage()).showAndWait();
        }
    }
}
