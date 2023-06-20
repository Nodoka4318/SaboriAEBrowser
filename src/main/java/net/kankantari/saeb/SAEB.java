package net.kankantari.saeb;

import javafx.application.Application;
import javafx.scene.control.Alert;
import net.kankantari.saeb.app.MainApp;
import net.kankantari.saeb.app.features.FeatureManager;
import net.kankantari.saeb.app.features.reading.FReadingPassageRetriever;
import net.kankantari.saeb.exceptions.SAEBException;

import java.util.ArrayList;
import java.util.Arrays;

public class SAEB {
    private static FeatureManager featureManager;
    private static Config config;
    private static AEClassMap mapping;

    public static void main(String[] args) {
        featureManager = new FeatureManager();

        if (!Config.isConfigExists()) {
            config = Config.getDefaultConfig();
            try {
                config.saveConfig();

                var userDir = System.getProperty("user.dir");
                System.out.println("Config file created: " + userDir + "/saeb/config.json");
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }

        try {
            config = Config.loadConfigFile();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        Config.setConfig(config);

        try {
            mapping = AEClassMap.loadMapping();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        Application.launch(MainApp.class);
    }

    public static FeatureManager getFeatureManager() {
        return featureManager;
    }

    public static AEClassMap getMapping() {
        return mapping;
    }
}