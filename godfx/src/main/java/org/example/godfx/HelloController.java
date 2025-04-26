package org.example.godfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class HelloController {

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
        if(!centerScrollPane.getChildrenUnmodifiable().contains(label)){
            centerScrollPane.setContent(label);
            label.setText("Testing ............");
        }
    }

    @FXML
    private void onLoadResourcesButtonClick(ActionEvent actionEvent) {

    }
}