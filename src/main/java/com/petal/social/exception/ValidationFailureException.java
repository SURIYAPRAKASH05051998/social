package com.petal.social.exception;

import com.petal.social.util.model.PetalsStatus;

public class ValidationFailureException extends RuntimeException {

    private static final long serialVersionUID = 1764517438829309168L;
    private static final int BAD_REQUEST = 400;

    private PetalsStatus petalsStatus;
    private int httpStatus;

    public ValidationFailureException(PetalsStatus error) {
        this(BAD_REQUEST, error);
    }

    public ValidationFailureException(int httpStatus, PetalsStatus petalsStatus) {
        super(petalsStatus.toString());
        this.httpStatus = httpStatus;
        this.petalsStatus = petalsStatus;
    }

    public PetalsStatus getPetalsStatus() {
        return petalsStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
