package net.kankantari.saeb.exceptions;

import java.io.Serial;

public class SAEBFileNotFoundException extends SAEBException {
    @Serial
    private static final long serialVersionUID = 1l;

    public SAEBFileNotFoundException(String filePath) {
        super("File, " + filePath + " was not found.");
    }
}
