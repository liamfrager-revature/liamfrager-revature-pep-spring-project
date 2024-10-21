package com.example.exception;

/**
 * Is thrown when a given password is less than 4 characters long.
 */
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("The given password is too short.");
    }
}