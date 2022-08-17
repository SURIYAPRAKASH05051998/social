package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class UploadServiceUnavailableException extends Exception {
    private static final long serialVersionUID = -3884444698097938553L;

    public UploadServiceUnavailableException() {
        super();
    }

    public UploadServiceUnavailableException(Throwable t) {
        super(t);
    }
}
