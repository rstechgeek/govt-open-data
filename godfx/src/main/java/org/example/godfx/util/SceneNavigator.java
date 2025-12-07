package org.example.godfx.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class for scene navigation.
 * Centralizes view switching logic.
 */
public class SceneNavigator {

    /**
     * Navigate to a different FXML view.
     * 
     * @param event    The action event from the button click
     * @param fxmlPath Path to the FXML file (relative to resources)
     * @throws IOException if FXML file cannot be loaded
     */
    public static void navigateTo(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    private SceneNavigator() {
        // Utility class
    }
}
