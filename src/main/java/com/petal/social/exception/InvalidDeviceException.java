package com.petal.social.exception;

public class InvalidDeviceException extends Exception {
    private static final long serialVersionUID = 9115713481899441481L;

    public InvalidDeviceException() {
        super();
    }

    public InvalidDeviceException(String message) {
        super(message);
    }
}
