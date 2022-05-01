package ru.itmo.client;

import ru.itmo.client.service.ReaderManager;
import ru.itmo.common.messages.MessageManager;

public class ClientLauncher {

    public static void main(String[] args) {
        // Запрос на включение дружественного интерфейса
        new MessageManager().turnOnFriendly();
        // Включение считывание с консоли
        ReaderManager.turnOnConsole();
        // Новый клиент
        Client client = new Client();
        //Запускаем логику клиента
        client.start();
    }
}
