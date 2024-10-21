package com.example.exception;

/**
 * Is thrown when a given username already exists.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("A user with that username already exists.");
    }
}
