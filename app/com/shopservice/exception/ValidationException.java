package com.shopservice.exception;

/**
 * Created by user50 on 29.06.2014.
 */
public class ValidationException extends RuntimeException {
    private Description description;

    public ValidationException(Description description)
    {
        this.description = description;
    }

    public Description getDescription() {
        return description;
    }
}
