package net.kankantari.saeb.app.view;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainView {
    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private WebView webView;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button subWebViewBackButton;

    @FXML
    private Button subWebViewForwardButton;

    @FXML
    private TextField subWebViewURLField;

    @FXML
    private Button subWebViewGoButton;

    @FXML
    private WebView subWebView;

    private static final String AE3_URL = "https://ae3.nagoya";

    public void initialize() {
        // button action
        backButton.setOnAction(e -> goBack());
        forwardButton.setOnAction(e -> goForward());
        subWebViewBackButton.setOnAction(e -> subWebViewGoBack());
        subWebViewForwardButton.setOnAction(e -> subWebViewGoForward());
        subWebViewGoButton.setOnAction(e -> loadSubWebView());


        // Load the main web page
        WebEngine webEngine = webView.getEngine();
        webEngine.load(AE3_URL);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                onWebViewPageChanged();
            }
        });
    }

    private void onWebViewPageChanged() {
        outputTextArea.setText(webView.getEngine().getLocation());
    }

    private void goBack() {
        WebEngine webEngine = webView.getEngine();
        if (webEngine.getHistory().getCurrentIndex() > 0) {
            webEngine.getHistory().go(-1);
        }
    }

    private void goForward() {
        WebEngine webEngine = webView.getEngine();
        if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
            webEngine.getHistory().go(1);
        }
    }

    private void subWebViewGoBack() {
        WebEngine webEngine = subWebView.getEngine();
        if (webEngine.getHistory().getCurrentIndex() > 0) {
            webEngine.getHistory().go(-1);
        }
    }

    private void subWebViewGoForward() {
        WebEngine webEngine = subWebView.getEngine();
        if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
            webEngine.getHistory().go(1);
        }
    }

    private void loadSubWebView() {
        String url = subWebViewURLField.getText();
        if (!url.isEmpty()) {
            WebEngine webEngine = subWebView.getEngine();
            webEngine.load(url);
        }
    }
}
