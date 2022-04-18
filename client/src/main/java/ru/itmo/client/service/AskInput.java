package ru.itmo.client.service;

import ru.itmo.client.Client;
import ru.itmo.client.handlers.InputHandler;
import ru.itmo.common.exceptions.EmptyInputException;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;

import java.io.IOException;
import java.util.Objects;

public class AskInput {
    private static boolean CONST_FRIENDLY_INTERFACE;
    private static boolean friendlyInterface;
    private final MessageManager msg = new MessageManager();

    /**
     * Метод, позволяющй включить дружественный интерфейс
     */
    public void turnOnFriendly() {
        try {
            CONST_FRIENDLY_INTERFACE = getBooleanInput(msg.askFriendly());
        } catch (Exception e) {
            msg.printErrorMessage(e);
            turnOnFriendly();
        }
    }

    /**
     * Метод, позволяющий выключить дружественный интерфейс
     */
    public static void turnOffFriendly() {
        friendlyInterface = false;
    }

    /**
     * Метод, возвращающийся к предыдущемей настройке дружественного интерфейса
     */
    public static void returnFriendly() {
        friendlyInterface = CONST_FRIENDLY_INTERFACE;
    }

    /**
     * Запрашивает ввод команды
     * @param in
     * @return
     */
    public static String askCommand(InputHandler in) {
        String command = null;
        while(command == null) {
            printMessage("Введите команду:");
            try {
                command = in.readInput();
            } catch (IOException e) {
//                ReaderManager.returnOnPreviousReader();
//                throw new EndException("Произошла ошибка, невозможно прочитать данные из файла.\n");
            }
        }
        return command;
    }
    /**
     * Внутренний метод для более удобного преобразования String в Boolean
     * @param input строка, которая будет преобразовываться в Boolean
     * @return true (если в строке присутствует true, yes, да вне зависимости от регистра), false (если в строке присутствует false, no, нет или если строка пустая)
     */
    private static Boolean getBooleanInput(String input) throws EmptyInputException, WrongArgumentException {
        if (input.equals("true") || input.equals("yes") || input.equals("да")) {
            return true;
        } else if (input.equals("false") || input.equals("no") || input.equals("нет")) {
            return false;
        } else if (!input.isEmpty()) throw new WrongArgumentException();
        else throw new EmptyInputException();
    }

    /**
     * Внутренний метод для вывода сообщения относительно friendlyInterface
     * @param message строка, которая будет напечатана, если дружественный интерфейс включен
     */
    private static void printMessage(String message){
        if (friendlyInterface) {
            System.out.println(message);
        }
    }
}
