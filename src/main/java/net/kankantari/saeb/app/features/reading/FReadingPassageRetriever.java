package net.kankantari.saeb.app.features.reading;

import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;

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
    public void onEvent(EnumEvent eventId, MainView view) {
        if (eventId == EnumEvent.HTML_UPDATED) {
            onHTMLUpdated(view);
        }
    }

    private void onHTMLUpdated(MainView view) {
        var webEngine = view.getWebView().getEngine();

        // retrieve passage
        if (WebUtil.getPageTitle(webEngine).contains("Reading Bank")) {
            var obj = webEngine.executeScript("document.getElementsByClassName('PassageComponent__root___1QWH3')[0].innerHTML");
            if (obj instanceof String src) {
                passages = Arrays.stream(src.split("<br><br>")).toList();
                var j = String.join("\n\n", passages);
                if (!j.equals(passage)) {
                    passage = j;
                    view.getSubWebView().getEngine().loadContent(src);
                    passageSet = true;
                    passageUpdated = true;
                }
            }
        }
    }
}
