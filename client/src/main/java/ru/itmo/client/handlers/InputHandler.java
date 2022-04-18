package ru.itmo.client.handlers;

import java.io.IOException;

public abstract class InputHandler {
    /**
     * Абстрактный метод read, переопределяемый для двух разных типов считывания - с консоли и с файла
     */
    public abstract String readInput() throws IOException;
}
