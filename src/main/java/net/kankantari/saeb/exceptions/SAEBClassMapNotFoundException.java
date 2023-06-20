package net.kankantari.saeb.exceptions;

import java.io.Serial;

public class SAEBClassMapNotFoundException extends SAEBException {
    @Serial
    private static final long serialVersionUID = 1l;

    public SAEBClassMapNotFoundException(String mapName) {
        super("Class mapping, " + mapName + " was not found.");
    }
}
