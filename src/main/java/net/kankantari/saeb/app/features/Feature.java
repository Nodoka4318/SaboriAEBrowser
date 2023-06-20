package net.kankantari.saeb.app.features;

import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.views.MainView;
import net.kankantari.saeb.exceptions.SAEBException;

public abstract class Feature {
    private String featureId;

    public Feature(String featureId) {
        this.featureId = featureId;
    }

    /**
     * いろんな時に実行されるやつ
     * @param eventId
     */
    public abstract void onEvent(EnumEvent eventId, MainView view) throws Exception ;

}
