package com.example.exception;

/**
 * Is thrown when there is no message with a given ID.
 */
public class InvalidMessageIDException extends Exception {
    public InvalidMessageIDException() {
        super("A message with that ID does not exist.");
    }
    public InvalidMessageIDException(String message) {
        super(message);
    }
}
