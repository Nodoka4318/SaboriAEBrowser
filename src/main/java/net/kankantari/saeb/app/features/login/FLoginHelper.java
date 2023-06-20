package net.kankantari.saeb.app.features.login;

import net.kankantari.saeb.Config;
import net.kankantari.saeb.app.EnumEvent;
import net.kankantari.saeb.app.features.Feature;
import net.kankantari.saeb.app.views.MainView;

public class FLoginHelper extends Feature {
    private boolean loggedIn = false;

    public FLoginHelper() {
        super("LoginHelper");
    }

    @Override
    public void onEvent(EnumEvent eventId, MainView view) {
        switch (eventId) {
            case TICK -> onTick(view);
        }
    }

    private boolean isCredentialSet() {
        return true; // TODO: ログイン情報の存在
    }

    private void onTick(MainView view) {
        if (loggedIn || !isCredentialSet()) {
            return;
        }

        var conf = Config.getConfig();
        if (!conf.isAutoLogin()) {
            return;
        }

        var webEngine = view.getWebView().getEngine();

        if (view.getLastPageUpdatedHTML().contains("login-box-container")) {
            webEngine.executeScript("document.getElementById('loginid').value = '" +  conf.getId() + "'");
            webEngine.executeScript("document.getElementById('password').value = '" + conf.getPassword() + "'");
            webEngine.executeScript("document.getElementById('loginForm').submit()");

            loggedIn = true;
        }
    }
}
