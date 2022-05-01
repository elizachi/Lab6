package ru.itmo.common.messages;

import ru.itmo.common.exceptions.WrongArgumentException;

import java.util.Scanner;

public class MessageManager {
    private static boolean CONST_FRIENDLY_INTERFACE;
    private static boolean friendlyInterface;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Включает дружественный интерфейс и запоминает изначальную его настройку
     */
    public void turnOnFriendly() {
        System.out.println("Включить дружественный интерфейс?");
        String answer = scanner.nextLine().trim()
                .split(" ")[0].toLowerCase();
        if(answer.isEmpty()) {
            CONST_FRIENDLY_INTERFACE = false;
        } else {
            CONST_FRIENDLY_INTERFACE = true;
        }
        friendlyInterface = CONST_FRIENDLY_INTERFACE;
    }

    /**
     * Выключает дружественный интерфейс
     */
    public static void turnOffFriendly() {
        friendlyInterface = false;
    }

    /**
     * Возвращает к изначальной настройке дружественного интерфейса
     */
    public static void returnFriendly() {
        friendlyInterface = CONST_FRIENDLY_INTERFACE;
    }

    public static boolean isFriendlyInterface() {
        return friendlyInterface;
    }

    /**
     * Внутренний метод для вывода сообщения относительно friendlyInterface
     */
    public void printMessage(String name){
        if (friendlyInterface) {
            System.out.println("Введите "+name+":");
        }
    }

    /**
     * Выводит сообщение, объясняющее природу вызванного исключения
     * @param e - исключение
     */
    public void printErrorMessage(WrongArgumentException e) {
//        if(friendlyInterface)  {
//            System.err.println(e.getType().getDescription());
//        }
        System.err.println(e.getType().getDescription());
    }

    public void printWarningMessage() {
        System.out.println("\u001B[33mВы ввели пустую строку. Поле примет значение null.\u001B[0m");
    }
    public void printWarningMessage(WrongArgumentException e) {
        System.out.println("\u001B[33m"+e.getType().getDescription()+"\u001B[0m");
    }
}