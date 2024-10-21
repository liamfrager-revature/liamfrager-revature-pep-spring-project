package com.example.exception;

/**
 * Is thrown when a given username is blank.
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException() {
        super("The given username is invalid.");
    }
}
