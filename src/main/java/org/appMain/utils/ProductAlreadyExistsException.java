package org.appMain.utils;

import java.io.IOException;

public class ProductAlreadyExistsException extends IOException {
    public ProductAlreadyExistsException() {
        super();
    }

    public ProductAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProductAlreadyExistsException(final String message) {
        super(message);
    }

    public ProductAlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
