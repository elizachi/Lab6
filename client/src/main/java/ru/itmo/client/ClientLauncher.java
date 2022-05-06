package ru.itmo.client;

import ru.itmo.client.service.ReaderManager;
import ru.itmo.common.messages.MessageManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Главный класс, запускающий логику клиента
 */
public class ClientLauncher {
    public static final Logger log = LogManager.getLogger(ClientLauncher.class.getName());
     public static void main(String[] args) {
         String ServerHost = null;
         int ServerPort = 0;

         // получение хоста и порта
         try {
             ServerHost = args[0].trim();
             ServerPort = Integer.parseInt(args[1].trim());
             System.out.println("Получены хост: " + ServerHost + " и порт: " + ServerPort);
         } catch (NumberFormatException exception){
             System.err.println("Порт должен быть числом.");
             return;
         } catch (ArrayIndexOutOfBoundsException exception){
             System.err.println("Недостаточно аргументов.");
             return;
         }

         // Новый клиент
         Client client = new Client(ServerHost, ServerPort);
        // Включение считывание с консоли
        ReaderManager.turnOnConsole();
        // Запрос на включение дружественного интерфейса
        new MessageManager().turnOnFriendly();
        //Запускаем логику клиента
        client.start();
    }
}
