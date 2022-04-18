package ru.itmo.common.exceptions;

public class WrongArgumentException extends Exception{
    public String getMessage() {
        return "Данные введены неверно.";
    }
}
