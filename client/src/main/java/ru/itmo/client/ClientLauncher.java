package ru.itmo.client;

import ru.itmo.client.service.ReaderManager;
import ru.itmo.common.messages.MessageManager;

/**
 * Главный класс, запускающий логику клиента
 */
public class ClientLauncher {
     public static void main(String[] args) {
        // Включение считывание с консоли
        ReaderManager.turnOnConsole();
        // Запрос на включение дружественного интерфейса
        new MessageManager().turnOnFriendly();
        // Новый клиент
        Client client = new Client();
        //Запускаем логику клиента
        client.start();
    }
}
