package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;

public class ClientLauncher {

    public static void main(String[] args) {
        // Запрос на включение дружественного интерфейса
        new AskInput().turnOnFriendly();
        // Включение считывание с консоли
        ReaderManager.turnOnConsole();
        // Новый клиент
        Client client = new Client();
        //Запускаем логику клиента
        client.start();
    }
}
