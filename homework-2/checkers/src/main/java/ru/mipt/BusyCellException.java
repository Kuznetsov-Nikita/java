package ru.mipt;

public class BusyCellException extends GeneralError {
    public BusyCellException() {}

    public BusyCellException(String message) {
        super(message);
    }
}
