package ru.itmo.common.exceptions;

public class EmptyInputException extends Exception{

    public String getMessage() {
        return "Вы ввели пустую строку.";
    }

}
