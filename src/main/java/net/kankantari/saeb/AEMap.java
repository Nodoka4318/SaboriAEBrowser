package net.kankantari.saeb;

import net.kankantari.saeb.exceptions.SAEBMappingNotFoundException;
import net.kankantari.saeb.exceptions.SAEBFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AEMap {
    private static class MapElem {
        public String Key;
        public String Value;

        private MapElem(String key, String value) {
            Key = key;
            Value = value;
        }

        public static MapElem fromString(String str) {
            var pars = str.split("::");
            return new MapElem(pars[0], pars[1]);
        }

        public String getStr() {
            return Key + "::" + Value;
        }
    }

    private List<MapElem> map;

    private AEMap(List<MapElem> map) {
        this.map = map;
    }

    public String get(String key) throws SAEBMappingNotFoundException {
        for (var m : map) {
            if (m.Key.toLowerCase(Locale.ROOT).equals(key.toLowerCase(Locale.ROOT))) {
                return m.Value;
            }
        }

        throw new SAEBMappingNotFoundException(key);
    }

    public static AEMap loadMapping() throws SAEBFileNotFoundException, IOException {
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

            return new AEMap(mapping);
        }
    }
}
