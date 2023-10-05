package net.kankantari.saeb.app.features.listeningbank;

import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBMappingNotFoundException;

import java.util.Arrays;
import java.util.List;

public class FListeningScriptRetriever extends Feature {
    private static List<String> sentences;
    private static String script = "";
    private static boolean scriptSet = false;
    private static boolean scriptUpdated = false;

    public FListeningScriptRetriever() {
        super("ListeningScriptRetriever");
    }

    public static List<String> getSentences() {
        return sentences;
    }

    public static String getScript() {
        return script;
    }

    public static boolean isScriptSet() {
        return scriptSet;
    }

    public static boolean isScriptUpdated() {
        return scriptUpdated;
    }

    @Override
    public void onEvent(EnumEvent eventId, MainView view) throws SAEBMappingNotFoundException {
        switch (eventId) {
            case HTML_UPDATED -> onHTMLUpdated(view);
            case LOCATION_CHANGED -> onLocationChanged();
        }
    }

    private void onHTMLUpdated(MainView view) throws SAEBMappingNotFoundException {
        var webEngine = view.getWebView().getEngine();
        var map = SAEB.getMapping();

        if (WebUtil.getPageTitle(webEngine).equals(map.get("ListeningComprehensionPageTitle"))) {
            var boxClass = map.get("ReadingPassageBox");

            var obj = WebUtil.getFirstClassContent(webEngine, boxClass);
            if (obj instanceof String src) {
                sentences = Arrays.stream(src.split("<br><br>")).toList();
                var j = String.join("\n\n", sentences);
                if (!scriptUpdated && !j.equals(script)) {
                    script = j;
                    view.getSubWebView().getEngine().loadContent(src);
                    scriptSet = true;
                    scriptUpdated = true;
                }
            }
        }
    }

    private void onLocationChanged() {
        scriptUpdated = false;
    }
}
