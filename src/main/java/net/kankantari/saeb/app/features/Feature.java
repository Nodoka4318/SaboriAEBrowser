package net.kankantari.saeb.app.features;

import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.view.MainView;

public abstract class Feature {
    private String featureId;

    public Feature(String featureId) {
        this.featureId = featureId;
    }

    /**
     * タイトルに含まれる識別子
     * @return
     */
    public abstract String getTitleKeyword();

    /**
     * いろんな時に実行されるやつ
     * @param eventId
     */
    public abstract void onEvent(EnumEvent eventId, MainView view);

}
