package net.kankantari.saeb.app.features.readingbank;

import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBMappingNotFoundException;

import java.util.Arrays;
import java.util.List;

public class FReadingPassageRetriever extends Feature {
    private static List<String> passages;
    private static String passage  = "";
    private static boolean passageSet = false;
    private static boolean passageUpdated = false;

    public FReadingPassageRetriever() {
        super("ReadingPassageRetriever");
    }

    public static List<String> getPassages() {
        return passages;
    }

    public static String getPassage() {
        return passage;
    }

    public static boolean isPassageSet() {
        return passageSet;
    }

    public static boolean isPassageUpdated() {
        return passageUpdated;
    }

    public static void togglePassageUpdated() {
        passageUpdated = !passageUpdated;
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

        // retrieve passage
        if (WebUtil.getPageTitle(webEngine).equals(map.get("ReadingSkimmingPageTitle"))) {
            var boxClass = map.get("ReadingPassageBox");

            var obj = WebUtil.getFirstClassContent(webEngine, boxClass);
            if (obj instanceof String src) {
                passages = Arrays.stream(src.split("<br><br>")).toList();
                var j = String.join("\n\n", passages);
                if (!passageUpdated && !j.equals(passage)) {
                    passage = j;
                    view.getSubWebView().getEngine().loadContent(src);
                    passageSet = true;
                    passageUpdated = true;
                }
            }
        }
    }

    private void onLocationChanged() {
        passageUpdated = false;
    }
}
