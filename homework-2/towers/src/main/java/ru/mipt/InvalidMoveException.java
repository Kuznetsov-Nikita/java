package ru.mipt;

public class InvalidMoveException extends GeneralError {
    public InvalidMoveException() {}

    public InvalidMoveException(String message) {
        super(message);
    }
}
