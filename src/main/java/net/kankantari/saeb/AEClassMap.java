package net.kankantari.saeb;

import net.kankantari.saeb.exceptions.SAEBClassMapNotFoundException;
import net.kankantari.saeb.exceptions.SAEBFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AEClassMap {
    private static class MapElem {
        public String Key;
        public String ClassName;

        private MapElem(String key, String className) {
            Key = key;
            ClassName = className;
        }

        public static MapElem fromString(String str) {
            var pars = str.split("::");
            return new MapElem(pars[0], pars[1]);
        }

        public String getStr() {
            return Key + "::" + ClassName;
        }
    }

    private List<MapElem> map;

    private AEClassMap(List<MapElem> map) {
        this.map = map;
    }

    public String get(String key) throws SAEBClassMapNotFoundException {
        for (var m : map) {
            if (m.Key.equals(key)) {
                return m.ClassName;
            }
        }

        throw new SAEBClassMapNotFoundException(key);
    }

    public static AEClassMap loadMapping() throws SAEBFileNotFoundException, IOException {
        var file = new File(Config.CONFIG_DIRECTORY_PATH + "/" + Config.getConfig().getMappingFile());

        if (!file.exists()) {
            throw new SAEBFileNotFoundException(file.getPath());
        } else {
            var mapping = new ArrayList<MapElem>();
            var allLines = Files.readAllLines(file.toPath());

            for (var l : allLines) {
                if (!l.isBlank() && !l.startsWith("#") && l.contains("::")) {
                    mapping.add(MapElem.fromString(l));
                }
            }

            return new AEClassMap(mapping);
        }
    }
}
