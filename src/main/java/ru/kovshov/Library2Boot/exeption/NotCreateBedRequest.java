package ru.kovshov.Library2Boot.exeption;

public class NotCreateBedRequest extends RuntimeException{
    public NotCreateBedRequest(String message) {
        super(message);
    }
}
