package net.kankantari.saeb.app.features.readingbank;

import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBMappingNotFoundException;

public class FReadingComprehensionHelper extends Feature {
    private String lastSrc = "";
    private boolean loaded = false;

    public FReadingComprehensionHelper() {
        super("ReadingComprehensionHelper");
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

        if (!loaded && WebUtil.getPageTitle(webEngine).equals(map.get("ReadingComprehensionPageTitle"))) {
            // questionsの取得
            var boxClass = map.get("ReadingComprehensionQuestionBox");

            var obj = WebUtil.getFirstClassContent(webEngine, boxClass);
            if (obj instanceof String src) {
                if (lastSrc.equals(src)) {
                    return;
                }

                lastSrc = src;
                src = src.replaceAll("class=", "")
                        .replaceAll("button", "p");

                view.getSubWebView().getEngine().loadContent(src);
                loaded = true;
            }
        }
    }

    private void onLocationChanged() {
        loaded = false;
    }
}
