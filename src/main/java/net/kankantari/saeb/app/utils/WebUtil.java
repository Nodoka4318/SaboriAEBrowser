package net.kankantari.saeb.app.utils;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

public class WebUtil {
    public static String getPageTitle(WebEngine webEngine) {
        return (String) webEngine.executeScript("document.title");
    }

    public static String[] getClassContents(WebEngine webEngine, String className) {
        return (String[]) webEngine.executeScript("" +
                "var elems = document.getElementsByClassName(\"" + className + "\");\n" +
                "var l = [];\n" +
                "for (var i = 0; i < elems.length; i++) {\n" +
                "    l.push(elems[i].innerHTML);\n" +
                "}\n" +
                "l;");
    }
}
