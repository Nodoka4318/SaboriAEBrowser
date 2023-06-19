package net.kankantari.saeb.app.features.reading;

import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.utils.WebUtil;
import net.kankantari.saeb.app.views.MainView;

public class FReadingComprehensionHelper extends Feature {
    private static final String BOX_CLASS_RETRIEVE = "document.getElementsByClassName('ReadingCombinationQuestionView_PC__combinationQuestion___dyQsz')[0].innerHTML";
    private String lastSrc = "";
    private boolean loaded = false;

    public FReadingComprehensionHelper() {
        super("ReadingComprehensionHelper");
    }


    @Override
    public void onEvent(EnumEvent eventId, MainView view) {
        if (eventId == EnumEvent.HTML_UPDATED) {
            onHTMLUpdated(view);
        } else if (eventId == EnumEvent.LOCATION_CHANGED) {
            onLocationChanged();
        }
    }

    private void onHTMLUpdated(MainView view) {
        var webEngine = view.getWebView().getEngine();

        if (WebUtil.getPageTitle(webEngine).equals("Academic Express3")) {
            var obj = webEngine.executeScript(BOX_CLASS_RETRIEVE);
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
