package com.example.exception;

/**
 * Is thrown when a message's posted_by ID doesn't have a corresponding user.
 */
public class InvalidUserIDException extends Exception {
    public InvalidUserIDException() {
        super("A user with that ID does not exist.");
    }
    public InvalidUserIDException(int id) {
        super("A user with ID '" + id + "' does not exist.");
    }
}
