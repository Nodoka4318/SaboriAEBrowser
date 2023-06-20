package net.kankantari.saeb.app.utils;

import javafx.scene.web.WebEngine;

public class WebUtil {
    public static String getPageTitle(WebEngine webEngine) {
        return (String) webEngine.executeScript("document.title");
    }

    /*
    public static String[] getClassContents(WebEngine webEngine, String className) {
        return (String[]) webEngine.executeScript("" +
                "var elems = document.getElementsByClassName(\"" + className + "\");\n" +
                "var l = [];\n" +
                "for (var i = 0; i < elems.length; i++) {\n" +
                "    l.push(elems[i].innerHTML);\n" +
                "}\n" +
                "l;");
    }

     */

    /**
     * this may throw exception when the web engine returns undefined
     * @param webEngine
     * @param className
     * @return
     */
    public static Object getFirstClassContent(WebEngine webEngine, String className) {
        return webEngine.executeScript("document.getElementsByClassName('" + className + "')[0].innerHTML");
    }

    public static String extractTextFromHTML(String htmlStr) {
        return htmlStr.replaceAll("<.*?>", "\n").replaceAll("\\n+", "\n");
    }
}
