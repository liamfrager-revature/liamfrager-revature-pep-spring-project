package com.example.exception;

/**
 * Is thrown when a given username and password is an invalid login.
 */
public class InvalidLoginException extends Exception {
    public InvalidLoginException() {
        super("Could not find a user with that username and password.");
    }
}