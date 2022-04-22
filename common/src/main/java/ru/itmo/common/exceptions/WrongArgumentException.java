package ru.itmo.common.exceptions;

public class WrongArgumentException extends AbstractException{

    public WrongArgumentException(TypeOfError type) {
        super(type);
    }
}
