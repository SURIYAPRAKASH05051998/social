package com.petal.social.exception;

public class HubOfflineException extends RuntimeException {

    private static final long serialVersionUID = 2602148051399946924L;

    public HubOfflineException() {
        super();
    }

    public HubOfflineException(String message) {
        super(message);
    }
}
