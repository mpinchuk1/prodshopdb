package org.appMain.utils;

import java.io.IOException;

public class NotEnoughQuantityException extends IOException {
    public NotEnoughQuantityException() {
        super();
    }

    public NotEnoughQuantityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotEnoughQuantityException(final String message) {
        super(message);
    }

    public NotEnoughQuantityException(final Throwable cause) {
        super(cause);
    }
}
