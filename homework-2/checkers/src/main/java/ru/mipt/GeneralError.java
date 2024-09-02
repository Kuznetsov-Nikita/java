package ru.mipt;

public class GeneralError extends Exception {
    public GeneralError() {}

    public GeneralError(String message) {
        super(message);
    }
}