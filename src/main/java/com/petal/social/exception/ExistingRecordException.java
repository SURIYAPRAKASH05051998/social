package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ExistingRecordException extends RuntimeException {

    private static final long serialVersionUID = 5955385792224094297L;

    public ExistingRecordException() {
        super();
    }

    public ExistingRecordException(String message) {
        super(message);
    }

    public ExistingRecordException(Throwable t) {
        super(t);
    }

    public ExistingRecordException(String message, Throwable t) {
        super(message, t);
    }

}
