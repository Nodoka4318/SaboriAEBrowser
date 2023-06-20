package net.kankantari.saeb.app.features.reading;

import net.kankantari.saeb.SAEB;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBClassMapNotFoundException;
import net.kankantari.saeb.exceptions.SAEBException;

public class FReadingComprehensionHelper extends Feature {
    private String lastSrc = "";
    private boolean loaded = false;

    public FReadingComprehensionHelper() {
        super("ReadingComprehensionHelper");
    }


    @Override
    public void onEvent(EnumEvent eventId, MainView view) throws SAEBClassMapNotFoundException {
        switch (eventId) {
            case HTML_UPDATED -> onHTMLUpdated(view);
            case LOCATION_CHANGED -> onLocationChanged();
        }
    }

    private void onHTMLUpdated(MainView view) throws SAEBClassMapNotFoundException {
        var webEngine = view.getWebView().getEngine();

        if (WebUtil.getPageTitle(webEngine).equals("Academic Express3")) {
            var boxClass = SAEB.getMapping().get("ReadingComprehensionQuestionBox");

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
