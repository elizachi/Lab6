package ru.itmo.common.exceptions;

public class AbstractException extends Exception{
    private final TypeOfError type;

    public AbstractException(TypeOfError type) {
        this.type = type;
    }

    public TypeOfError getType() {
        return type;
    }
}
