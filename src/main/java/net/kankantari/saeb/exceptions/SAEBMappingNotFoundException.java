package net.kankantari.saeb.exceptions;

import java.io.Serial;

public class SAEBMappingNotFoundException extends SAEBException {
    @Serial
    private static final long serialVersionUID = 1l;

    public SAEBMappingNotFoundException(String mapName) {
        super("Mapping, " + mapName + " was not found.");
    }
}
