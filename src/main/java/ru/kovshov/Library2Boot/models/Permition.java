package ru.kovshov.Library2Boot.models;

public enum Permition {
    READ("read"),
    WRITE("write");

    private final String permition;


    Permition(String permition) {
        this.permition = permition;
    }

    public String getPermition(){
        return this.permition;
    }
}
