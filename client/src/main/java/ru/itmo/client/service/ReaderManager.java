package ru.itmo.client.service;

import ru.itmo.client.handlers.ConsoleInputHandler;
import ru.itmo.client.handlers.InputHandler;

import java.util.ArrayList;

public class ReaderManager {
    private static InputHandler reader;
    private static final ArrayList<InputHandler> handlers = new ArrayList<>();

    public static InputHandler getReader() {
        return reader;
    }

    public static void removeLast() {
        handlers.remove(handlers.size() - 1);
    }
    /**
     * Меняет тип считывания на считывание с консоли
     */
    public static void turnOnConsole() {
        // новый экземпляр класса считывания
        reader = new ConsoleInputHandler();
        // добавляем в массив хендлеров, чтобы потом к нему вернуться
        handlers.add(reader);
        // Возврат к дружественному интерфейсу после считывания с файла, если оно было
        AskInput.returnFriendly();
    }

    public static void returnOnPreviousReader() {
        reader = handlers.get(handlers.size()-1);
        AskInput.returnFriendly();
//        AskInput.removeLastHistory();
    }

}
