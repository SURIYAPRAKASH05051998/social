package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UpgradeException extends RuntimeException {
    private static final long serialVersionUID = 1186473881410449608L;

    public UpgradeException() {
        super();
    }

    public UpgradeException(String message) {
        super(message);
    }

    public UpgradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpgradeException(Throwable cause) {
        super(cause);
    }
}
