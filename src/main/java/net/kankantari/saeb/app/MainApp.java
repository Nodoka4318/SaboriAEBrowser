package net.kankantari.saeb.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.kankantari.saeb.app.view.MainView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        var is = classloader.getResource("mainview.fxml");

        var fxml = new FXMLLoader(is);
        var scene = new Scene(fxml.load(), 640, 480);
        scene.getRoot().setStyle("-fx-font-family: 'sans-serif'");
        scene.getStylesheets().add(classloader.getResource("style.css").toExternalForm());
        primaryStage.setTitle("Sabori AE Browser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
