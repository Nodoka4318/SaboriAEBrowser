package net.kankantari.saeb;

import javafx.application.Application;
import net.kankantari.saeb.app.MainApp;
import net.kankantari.saeb.app.features.FeatureManager;

public class SAEB {
    private static FeatureManager featureManager;

    public static void main(String[] args) {
        featureManager = new FeatureManager();

        Application.launch(MainApp.class);
    }

    public static FeatureManager getFeatureManager() {
        return featureManager;
    }
}