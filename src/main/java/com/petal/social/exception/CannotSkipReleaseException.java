package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotSkipReleaseException extends Exception {

    private static final long serialVersionUID = 8366169069464327968L;

    public CannotSkipReleaseException(String message) {
        super(message);
    }
}
