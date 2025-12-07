package org.example.godfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.application.Platform;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML
    private ScrollPane centerScrollPane;
    @FXML
    private Accordion leftAccordion;
    @FXML
    private Button refreshResourcesButton;
    @FXML
    private Button loadResourcesButton;

    @FXML
    private void onRefreshResourcesButtonClick(ActionEvent actionEvent) {
        Label label = new Label();
        if (!centerScrollPane.getChildrenUnmodifiable().contains(label)) {
            centerScrollPane.setContent(label);
            label.setText("Testing ............");
        }
    }

    @FXML
    private void onLoadResourcesButtonClick(ActionEvent actionEvent) {
        // Ensure ScrollPane stretches content
        centerScrollPane.setFitToWidth(true);
        centerScrollPane.setFitToHeight(true);

        // Prepare VBox for results
        javafx.scene.layout.VBox resultsBox = new javafx.scene.layout.VBox();
        resultsBox.setAlignment(javafx.geometry.Pos.CENTER);
        centerScrollPane.setContent(resultsBox);

        Label statusLabel = new Label("Starting load...");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        resultsBox.getChildren().add(statusLabel);

        // Async HTTP call
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8091/resources/load"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                .thenApply(HttpResponse::body)
                .thenAccept(lines -> {
                    lines.forEach(line -> Platform.runLater(() -> {
                        statusLabel.setText(line);
                        logger.info(line);
                    }));

                    // Final update
                    Platform.runLater(() -> {
                        statusLabel.setText(statusLabel.getText() + "\n--- Load Complete ---");
                    });
                })
                .exceptionally(e -> {
                    Platform.runLater(() -> {
                        statusLabel.setText("Error: " + e.getMessage());
                        logger.error("Error fetching resources: ", e);
                    });
                    return null;
                });
    }
}