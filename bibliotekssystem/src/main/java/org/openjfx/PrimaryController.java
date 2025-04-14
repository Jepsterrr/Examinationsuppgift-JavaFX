package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {
        statusLabel.setText("Du är inloggad!");
    }
}