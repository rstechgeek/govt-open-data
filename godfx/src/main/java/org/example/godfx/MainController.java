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
    private javafx.scene.control.TitledPane orgPane;
    @FXML
    private javafx.scene.control.ListView<String> orgListView;
    @FXML
    private javafx.scene.control.TitledPane sectorPane;
    @FXML
    private javafx.scene.control.ListView<String> sectorListView;

    public void initialize() {
        orgPane.expandedProperty().addListener((obs, wasExpanded, isExpanded) -> {
            if (isExpanded) {
                fetchAndPopulate("http://localhost:8091/resources/orgs", orgListView, "orgName");
            }
        });

        sectorPane.expandedProperty().addListener((obs, wasExpanded, isExpanded) -> {
            if (isExpanded) {
                fetchAndPopulate("http://localhost:8091/resources/sectors", sectorListView, "sectorName");
            }
        });
    }

    private void fetchAndPopulate(String url, javafx.scene.control.ListView<String> listView, String key) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    // Simple JSON parsing (assuming classpath has jackson or similar provided by
                    // parent,
                    // or using basic string parsing for simplicity to avoid adding dependencies if
                    // unnecessary.
                    // Given the project has jackson in resource-service, assuming godfx might not.
                    // Let's use basic regex/string parsing for this demo or better yet, just show
                    // raw for now
                    // or assume valid JSON format [ { "key": "value", ... }, ... ]

                    // Actually, let's try to parse it properly if possible, but without extra deps.
                    // We will just clear and add raw string for now, or do a simple list
                    // extraction.
                    // Real implementation should use a JSON library.

                    Platform.runLater(() -> {
                        listView.getItems().clear();
                        listView.getItems().add("Data fetched. Parsing...");

                        // Rudimentary parsing for display
                        String[] items = body.split("\\},\\{");
                        for (String item : items) {
                            // extract name
                            int nameIndex = item.indexOf(key);
                            if (nameIndex != -1) {
                                int start = item.indexOf(":", nameIndex) + 2;
                                int end = item.indexOf("\"", start);
                                if (end > start) {
                                    listView.getItems().add(item.substring(start, end));
                                }
                            }
                        }
                        if (listView.getItems().size() > 1)
                            listView.getItems().remove(0); // remove "Data fetched..."
                    });
                })
                .exceptionally(e -> {
                    Platform.runLater(() -> listView.getItems().add("Error: " + e.getMessage()));
                    return null;
                });
    }

    @FXML
    private void onLoadResourcesButtonClick(ActionEvent actionEvent) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("view/load_resources.fxml"));
            javafx.scene.Parent loadView = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene()
                    .getWindow();
            stage.getScene().setRoot(loadView);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            logger.error("Failed to load load_resources.fxml", e);
        }
    }
}