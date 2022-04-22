package ru.itmo.common.exceptions;

public enum TypeOfError {
    OUT_OF_RANGE("Заданное значение находится вне допустимого диапазона значений"), // находится вне допустимого диапазона
    WRONG_TYPE("Тип введённых данных не соответствует ожидаемому"), // некорректный тип данных
    UNKNOWN("Введённую строку невозможно распознать"),
    EMPTY("Введена пустая строка"); // нераспознаваемая строка данных

    private final String description;

    TypeOfError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
