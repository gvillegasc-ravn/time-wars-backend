package com.ravn.timewars.shared.exception;

public class InvalidTimeEntryException extends RuntimeException {
    public InvalidTimeEntryException(String message) {
        super(message);
    }
}
