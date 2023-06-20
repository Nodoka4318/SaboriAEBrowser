package net.kankantari.saeb.app.features.vocabularybank;

import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBClassMapNotFoundException;

public class FVocabularyBankHelper extends Feature {
    private static final String WEBLIO_URL_BASE = "https://ejje.weblio.jp/content/";
    private String lastWord = "";

    public FVocabularyBankHelper() {
        super("VocabularyBankHelper");
    }

    @Override
    public void onEvent(EnumEvent eventId, MainView view) throws SAEBClassMapNotFoundException {
        switch (eventId) {
            case HTML_UPDATED -> onHTMLUpdated(view);
        }
    }

    private String getWeblioPage(String word) {
        return WEBLIO_URL_BASE + word;
    }

    private void onHTMLUpdated(MainView view) throws SAEBClassMapNotFoundException {
        var webEngine = view.getWebView().getEngine();
        if (WebUtil.getPageTitle(webEngine).contains("Vocabulary Bank")) {
                /*
                var word = WebUtil.getClassContents(webEngine, "MultipleChoiceQuestionBuilder__question___3Xy0n lang-ja")[0];
                view.setSubWebViewPage(getWeblioPage(word));
                */

            var boxClass = SAEB.getMapping().get("VocabularyBankWordBox");

            var obj = WebUtil.getFirstClassContent(webEngine, boxClass);
            if (obj instanceof String word) {
                if (!lastWord.equals(word)) {
                    lastWord = word;
                    view.setSubWebViewPage(getWeblioPage(word));
                }
            }
        }
    }
}
