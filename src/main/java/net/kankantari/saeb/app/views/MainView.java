package net.kankantari.saeb.app.views;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.kankantari.saeb.Config;
import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.MainApp;

import java.util.Timer;
import java.util.TimerTask;

public class MainView {
    private final Config conf = Config.getConfig();

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

    private Timer timer;

    private String lastPageUpdatedHTML = "";

    private String lastRecordedHTML = "";

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    public Button getForwardButton() {
        return forwardButton;
    }

    public void setForwardButton(Button forwardButton) {
        this.forwardButton = forwardButton;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public TextArea getOutputTextArea() {
        return outputTextArea;
    }

    public void setOutputTextArea(TextArea outputTextArea) {
        this.outputTextArea = outputTextArea;
    }

    public Button getSubWebViewBackButton() {
        return subWebViewBackButton;
    }

    public void setSubWebViewBackButton(Button subWebViewBackButton) {
        this.subWebViewBackButton = subWebViewBackButton;
    }

    public Button getSubWebViewForwardButton() {
        return subWebViewForwardButton;
    }

    public void setSubWebViewForwardButton(Button subWebViewForwardButton) {
        this.subWebViewForwardButton = subWebViewForwardButton;
    }

    public TextField getSubWebViewURLField() {
        return subWebViewURLField;
    }

    public void setSubWebViewURLField(TextField subWebViewURLField) {
        this.subWebViewURLField = subWebViewURLField;
    }

    public Button getSubWebViewGoButton() {
        return subWebViewGoButton;
    }

    public void setSubWebViewGoButton(Button subWebViewGoButton) {
        this.subWebViewGoButton = subWebViewGoButton;
    }

    public WebView getSubWebView() {
        return subWebView;
    }

    public void setSubWebView(WebView subWebView) {
        this.subWebView = subWebView;
    }

    public String getLastPageUpdatedHTML() {
        return lastPageUpdatedHTML;
    }

    public void initialize() {
        // button action
        backButton.setOnAction(e -> goBack());
        forwardButton.setOnAction(e -> goForward());
        subWebViewBackButton.setOnAction(e -> subWebViewGoBack());
        subWebViewForwardButton.setOnAction(e -> subWebViewGoForward());
        subWebViewGoButton.setOnAction(e -> loadSubWebView());

        var subWebEngine = subWebView.getEngine();
        subWebEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");
        subWebEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                onSubWebViewPageChanged();
            }
        });

        var webEngine = webView.getEngine();
        webEngine.load(conf.getAe3Url());
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                onWebViewPageChanged();
            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (MainApp.getMainStage().isShowing()) {
                        onTick();
                    } else {
                        timer.cancel();
                    }
                });
            }
        }, 0, 20);
    }

    public String getCurrentHTML() {
        var obj = webView.getEngine().executeScript("document.documentElement.outerHTML");
        if (obj instanceof String src) {
            return src;
        }
        return "";
    }

    private void onWebViewPageChanged() {
        // outputTextArea.setText(webView.getEngine().getLocation());
        SAEB.getFeatureManager().executeOnEvent(EnumEvent.PAGE_UPDATE, this);

        var obj = webView.getEngine().executeScript("document.documentElement.outerHTML");
        if (obj instanceof String src) {
            lastPageUpdatedHTML = src;
            lastRecordedHTML = src;
        }
    }

    private void onSubWebViewPageChanged() {
        subWebViewURLField.setText(subWebView.getEngine().getLocation());
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
            if (!url.contains("://")) {
                url = "http://" + url;
                subWebViewURLField.setText(url);
            }

            WebEngine webEngine = subWebView.getEngine();
            webEngine.load(url);
        }
    }

    public void setSubWebViewPage(String url) {
        subWebView.getEngine().load(url);
        subWebViewURLField.setText(url);
    }

    public void onTick() {
        SAEB.getFeatureManager().executeOnEvent(EnumEvent.TICK, this);
    }

    public boolean isHTMLUpdated() {
        var currentHTML = getCurrentHTML();

        if (!lastRecordedHTML.equals(currentHTML)) {
            lastRecordedHTML = currentHTML;

            return true;
        } else {
            return false;
        }
    }
}
