package net.kankantari.saeb.app.features;

import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.autologin.FLoginHelper;
import net.kankantari.saeb.app.features.vocabularybank.FVocabularyBankHelper;
import net.kankantari.saeb.app.views.MainView;

import java.util.ArrayList;

public class FeatureManager {
    private ArrayList<Feature> featureList;

    public FeatureManager() {
        featureList = new ArrayList<>();

        featureList.add(new FVocabularyBankHelper());
        featureList.add(new FLoginHelper());
    }

    public void executeOnEvent(EnumEvent eventId, MainView mainView) {
        for (var f : featureList) {
            f.onEvent(eventId, mainView);
        }
    }
}
