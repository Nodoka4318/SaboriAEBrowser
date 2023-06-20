package net.kankantari.saeb.exceptions;

import java.io.Serial;

public class SAEBException extends Exception {
    @Serial
    private static final long serialVersionUID = 1l;

    public SAEBException(String message) {
        super(message);
    }
}
