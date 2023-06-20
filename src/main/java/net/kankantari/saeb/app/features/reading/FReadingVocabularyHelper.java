package net.kankantari.saeb.app.features.reading;

import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBClassMapNotFoundException;

import java.util.ArrayList;

public class FReadingVocabularyHelper extends Feature {
    private String last = "";

    public FReadingVocabularyHelper() {
        super("ReadingVocabularyHelper");
    }

    @Override
    public void onEvent(EnumEvent eventId, MainView view) throws SAEBClassMapNotFoundException {
        switch (eventId) {
            case HTML_UPDATED -> onHTMLUpdated(view);
        }
    }

    private void onHTMLUpdated(MainView view) throws SAEBClassMapNotFoundException {
        var webEngine = view.getWebView().getEngine();

        if (WebUtil.getPageTitle(webEngine).equals("Academic Express3")) {
            var boxClass = SAEB.getMapping().get("ReadingVocabularyPassageBox");

            var obj = WebUtil.getFirstClassContent(webEngine, boxClass);
            if (obj instanceof String src) {
                if (!FReadingPassageRetriever.isPassageSet()) {
                    view.getOutputTextArea().setText("まだ本文が読み込まれていません。先にChallenge Testを受講してください。");
                    return;
                }

                if (last.equals(src)) {
                    return;
                }

                last = src;

                var passages = FReadingPassageRetriever.getPassages();
                var placeHolders = src
                        .replaceAll("<span class=\"MatchingQuestionBuilder__insertionPosition___24xfv\">（　　　　　　）</span>", "")
                        .replaceAll("<span>", "")
                        .replaceAll("<br>", "")
                        .replaceAll(" ", "")
                        .split("</span>");

                var ind = new ArrayList<String>();
                for (var pa : passages) {
                    var p = pa.replaceAll(" ", "");
                    for (var pl : placeHolders) {
                        if (!pl.equals(".") && !pl.equals(""))
                            p = p.replace(pl, "++");
                    }
                    ind.add(p);
                }

                var words = String.join("++", ind).split("\\+\\+");
                StringBuilder output = new StringBuilder();
                for (var w : words) {
                    if (w.length() > 0 && w.length() < 30) {
                        output.append(w).append(", ");
                    }
                }

                view.getOutputTextArea().setText(output.toString());

                FReadingPassageRetriever.togglePassageUpdated();
            }
        }
    }
}
