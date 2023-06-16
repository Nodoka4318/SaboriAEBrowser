package net.kankantari.saeb.app.features;

import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.vocabularybank.FVocabularyBankHelper;
import net.kankantari.saeb.app.view.MainView;

import java.util.ArrayList;

public class FeatureManager {
    private ArrayList<Feature> featureList;

    public FeatureManager() {
        featureList = new ArrayList<>();

        featureList.add(new FVocabularyBankHelper());
    }

    public void executeOnEvent(EnumEvent eventId, MainView mainView) {
        for (var f : featureList) {
            f.onEvent(eventId, mainView);
        }
    }
}
