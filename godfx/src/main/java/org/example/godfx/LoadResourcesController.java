package org.example.godfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoadResourcesController {

    @FXML
    private Label totalResourcesLabel;
    @FXML
    private Label lastLoadedLabel;
    @FXML
    private TextArea logTextArea;

    public void initialize() {
        refreshStats();
    }

    private void refreshStats() {
        HttpClient client = HttpClient.newHttpClient();

        // Fetch Count
        client.sendAsync(
                HttpRequest.newBuilder().uri(URI.create("http://localhost:8091/resources/count")).GET().build(),
                HttpResponse.BodyHandlers.ofString())
                .thenAccept(resp -> Platform.runLater(() -> totalResourcesLabel.setText(resp.body())));

        // Fetch Last Load Time
        client.sendAsync(HttpRequest.newBuilder().uri(URI.create("http://localhost:8091/resources/last-load-time"))
                .GET().build(), HttpResponse.BodyHandlers.ofString())
                .thenAccept(resp -> Platform.runLater(() -> lastLoadedLabel.setText(resp.body())));
    }

    @FXML
    private void onLoadResourcesClick(ActionEvent event) {
        logTextArea.clear();
        logTextArea.appendText("Starting load...\n");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8091/resources/load"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                .thenAccept(resp -> {
                    resp.body().forEach(line -> Platform.runLater(() -> logTextArea.appendText(line + "\n")));
                    Platform.runLater(() -> {
                        logTextArea.appendText("Load Complete.\n");
                        refreshStats();
                    });
                })
                .exceptionally(e -> {
                    Platform.runLater(() -> logTextArea.appendText("Error: " + e.getMessage() + "\n"));
                    return null;
                });
    }

    @FXML
    private void onCleanResourcesClick(ActionEvent event) {
        logTextArea.appendText("Cleaning resources...\n");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8091/resources/all"))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(resp -> Platform.runLater(() -> {
                    logTextArea.appendText("Resources cleaned.\n");
                    refreshStats();
                }))
                .exceptionally(e -> {
                    Platform.runLater(() -> logTextArea.appendText("Error cleaning: " + e.getMessage() + "\n"));
                    return null;
                });
    }

    @FXML
    private void onBackButtonClick(ActionEvent event) {
        try {
            Parent mainView = FXMLLoader.load(getClass().getResource("view/main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(mainView);
        } catch (IOException e) {
            e.printStackTrace();
            logTextArea.appendText("Error returning to main view: " + e.getMessage());
        }
    }
}
