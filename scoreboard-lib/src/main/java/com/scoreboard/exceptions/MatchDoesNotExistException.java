package com.scoreboard.exceptions;

public class MatchDoesNotExistException extends RuntimeException {
    public MatchDoesNotExistException(String message) {
        super(message);
    }
}
