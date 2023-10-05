package net.kankantari.saeb;

import javafx.application.Application;
import net.kankantari.saeb.app.MainApp;
import net.kankantari.saeb.app.features.FeatureManager;

public class SAEB {
    private static FeatureManager featureManager;
    private static Config config;
    private static AEMap mapping;

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
            mapping = AEMap.loadMapping();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        Application.launch(MainApp.class);
    }

    public static FeatureManager getFeatureManager() {
        return featureManager;
    }

    public static AEMap getMapping() {
        return mapping;
    }
}