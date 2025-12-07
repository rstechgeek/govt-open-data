module org.example.godfx {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.godfx to javafx.fxml;
    opens org.example.godfx.model to com.fasterxml.jackson.databind;

    exports org.example.godfx;
}