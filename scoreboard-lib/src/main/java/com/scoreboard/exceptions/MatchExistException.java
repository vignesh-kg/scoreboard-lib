package com.scoreboard.exceptions;

public class MatchExistException extends RuntimeException {
    public MatchExistException(String message) {
        super(message);
    }
}
