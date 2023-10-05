package net.kankantari.saeb.app.features;

import javafx.scene.control.ButtonType;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.listeningbank.FListeningScriptRetriever;
import net.kankantari.saeb.app.features.login.FLoginHelper;
import net.kankantari.saeb.app.features.readingbank.FReadingComprehensionHelper;
import net.kankantari.saeb.app.features.readingbank.FReadingComprehensionPassageRetriever;
import net.kankantari.saeb.app.features.readingbank.FReadingPassageRetriever;
import net.kankantari.saeb.app.features.readingbank.FReadingVocabularyHelper;
import net.kankantari.saeb.app.features.vocabularybank.FVocabularyBankHelper;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBException;

import java.util.ArrayList;

public class FeatureManager {
    private ArrayList<Feature> featureList;

    public FeatureManager() {
        featureList = new ArrayList<>();

        featureList.add(new FVocabularyBankHelper());
        featureList.add(new FLoginHelper());
        featureList.add(new FReadingPassageRetriever());
        featureList.add(new FReadingVocabularyHelper());
        featureList.add(new FReadingComprehensionHelper());
        featureList.add(new FReadingComprehensionPassageRetriever());
        featureList.add(new FListeningScriptRetriever());
    }

    public void executeOnEvent(EnumEvent eventId, MainView mainView) {
        for (var f : featureList) {
            try {
                f.onEvent(eventId, mainView);
            } catch (SAEBException e) {
                MainView.showDialog(e.getMessage(), ButtonType.CLOSE);
                e.printStackTrace();
            } catch (Exception ignored) {

            }
        }

        if (eventId == EnumEvent.HTML_UPDATED) {
            MainView.updateLastRecordedHTML(mainView);
        }

        if (eventId == EnumEvent.LOCATION_CHANGED) {
            MainView.updateLastRecordedLocation(mainView);
        }
    }
}
