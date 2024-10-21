package com.example.exception;

/**
 * Is thrown when a message's text is either empty or longer than 255 characters.
 */
public class InvalidMessageTextException extends Exception {
    public InvalidMessageTextException() {
        super("The message text is either empty or longer than 255 characters.");
    }
    public InvalidMessageTextException(String message) {
        super(message);
    }
}
