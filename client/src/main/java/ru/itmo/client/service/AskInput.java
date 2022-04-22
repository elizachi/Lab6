package ru.itmo.client.service;

import ru.itmo.client.handlers.InputHandler;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.Coordinates;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AskInput {
    private static boolean CONST_FRIENDLY_INTERFACE;
    private static boolean friendlyInterface;
    private final MessageManager msg = new MessageManager();

    /**
     * Метод, позволяющй включить дружественный интерфейс
     */
    public void turnOnFriendly() {
        try {
            CONST_FRIENDLY_INTERFACE = toBoolean(msg.askFriendly(), false);
        } catch (WrongArgumentException e) {
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

    public void InputManager(CommandType command, InputHandler in) {
        try {
            for(String function: command.getCommandFields()) {
                Method method = this.getClass().getDeclaredMethod(function, InputHandler.class);
                method.setAccessible(true);
                method.invoke(this, in);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println("Pizdec");
        }

    }
    /**
     * Запрашивает ввод команды и валидирует введённую пользователем строку
     * @param in - тип считывания (с консоли или с файла)
     * @return индекс команды, если она была найдена - иначе запрашивает повторный ввод
     */
    public int askCommand(InputHandler in) {
        int input = -1; // до того как индекс нужной команды был найден
        while(input == -1) {
            printMessage("Введите команду:");
            try {
                input = isCorrectCommand(in.readInput());
            } catch (IOException e) {
//                ReaderManager.returnOnPreviousReader();
//                throw new EndException("Произошла ошибка, невозможно прочитать данные из файла.\n");
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                input = -1;
            }
        }
        return input;
    }

    /**
     * Запрашивает ввод полей и валидирует введённое пользователем значение
     * @param in - тип считывания (с консоли или с файла)
     * @return поле, если оно было введено верно
     */
    private int askId(InputHandler in) {
        int input = -1;
        while(input == -1) {
            printMessage("Введите id:");
            try {
                input = isCorrectInteger(in.readInput(), 0);
            } catch(IOException e) {

            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                input = -1;
            }
        }
        return input;
    }

    private String askName(InputHandler in) {
        String name = null;
        while (name == null) {
            printMessage("Введите имя:");
            try {
                name = in.readInput();
                if(name.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            } catch (IOException e) {

            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                name = null;
            }
        }
        return name;
    }

    private String askSoundtrackName(InputHandler in) {
        String name = null;
        while (name == null) {
            printMessage("Введите название саундтрека:");
            try {
                name = in.readInput();
                if(name.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            } catch (IOException e) {

            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                name = null;
            }
        }
        return name;
    }

    private Long askMinutesOfWaiting(InputHandler in) {
        Long minutes = null;
        while (minutes == null) {
            printMessage("Введите минуты ожидания:");
            try {
                minutes = isCorrectLong(in.readInput(), -1);
            } catch (IOException e) {

            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                minutes = null;
            }
        }
        return minutes;
    }

    private int askImpactSpeed(InputHandler in) {
        int speed = -1;
        while(speed == -1) {
            printMessage("Введите скорость:");
            try {
                speed = isCorrectInteger(in.readInput(), -1);
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                speed = -1;
            } catch (IOException e) {

            }
        }
        return speed;
    }

    private Boolean askRealHero(InputHandler in) {
        Boolean realHero = null;
        while (realHero == null) {
            printMessage("Был ли он героем?");
            try {
                realHero = toBoolean(in.readInput(), false);
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                realHero = null;
            } catch (IOException e) {

            }
        }
        return realHero;
    }

    private Boolean askHasToothpick(InputHandler in) {
        Boolean realHero = null;
        boolean flag = true;
        while (flag) {
            printMessage("Был ли он героем?");
            try {
                realHero = toBoolean(in.readInput(), true);
                flag = false;
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                flag = true;
            } catch (IOException e) {

            }
        }
        return realHero;
    }

    private Coordinates askCoordinates(InputHandler in) {
        printMessage("Для определения местоположения персонажа введите координаты.");
        int x = 0;
        boolean flag = true;
        while(flag) {
            printMessage("Введите координату x:");
            try {
                x = isCorrectInteger(in.readInput());
                flag = false;
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
                flag = true;
            } catch (IOException e) {

            }
        }
        Float y = null;
        flag = true;
        while(flag) {
            printMessage("Введите координату y:");
            try {
                y = isCorrectFloat(in.readInput(), -188);
                flag = false;
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
                flag = true;
            } catch (IOException e) {

            }
        }
        return new Coordinates(x, y);
    }
    /**
     * Внутренний метод для более удобного преобразования String в Boolean
     * @param input строка, которая будет преобразовываться в Boolean
     * @return true (если в строке присутствует true, yes, да вне зависимости от регистра), false (если в строке присутствует false, no, нет или если строка пустая)
     */
    private Boolean toBoolean(String input, boolean hasNull) throws WrongArgumentException {
        if (input.equals("true") || input.equals("yes") || input.equals("да")) {
            return true;
        } else if (input.equals("false") || input.equals("no") || input.equals("нет")) {
            return false;
        } else if (input.isEmpty() && !hasNull) {
            throw new WrongArgumentException(TypeOfError.EMPTY);
        } else if(input.isEmpty()) {
            msg.printWarningMessage();
            return null;
        }
        else {
            throw new WrongArgumentException(TypeOfError.UNKNOWN);
        }
    }

    /**
     * Функция для проверки валидности введённого целочисленного значения
     * @param input - строка, введённая пользователем
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     * @throws WrongArgumentException
     */
    private Integer isCorrectInteger(String input) throws WrongArgumentException {
        try {
            Integer.parseInt(input);
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            else throw new WrongArgumentException(TypeOfError.WRONG_TYPE);
        }
        return Integer.parseInt(input);
    }

    /**
     * Функция для проверки валидности введённого целого числа с установкой нижней границы
     * @param input - строка, введённая пользователем
     * @param begin - нижняя граница для данного поля
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     * @throws WrongArgumentException
     */
    private Integer isCorrectInteger(String input, int begin) throws WrongArgumentException {
        try {
            if (Integer.parseInt(input) <= begin) {
                throw new WrongArgumentException(TypeOfError.OUT_OF_RANGE);
            }
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            else throw new WrongArgumentException(TypeOfError.WRONG_TYPE);
        }
        return Integer.parseInt(input);
    }

    /**
     * Функция для проверки валидности введённого целого числа с установкой нижней границы
     * @param input - строка, введённая пользователем
     * @param begin - нижняя граница для данного поля
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     * @throws WrongArgumentException
     */
    private Long isCorrectLong(String input, int begin) throws WrongArgumentException {
        try {
            if (Integer.parseInt(input) <= begin) {
                throw new WrongArgumentException(TypeOfError.OUT_OF_RANGE);
            }
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            else throw new WrongArgumentException(TypeOfError.WRONG_TYPE);
        }
        return Long.parseLong(input);
    }

    /**
     * Функция для проверки валидности введённого дробного числа с установкой нижней границы
     * @param input - строка, введённая пользователем
     * @param begin - нижняя граница для данного поля
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     * @throws WrongArgumentException
     */
    private Float isCorrectFloat(String input, int begin) throws WrongArgumentException {
        try {
            if (Float.parseFloat(input) <= begin) {
                throw new WrongArgumentException(TypeOfError.OUT_OF_RANGE);
            }
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            else throw new WrongArgumentException(TypeOfError.WRONG_TYPE);
        }
        return Float.parseFloat(input);
    }

    private int isCorrectCommand(String input) throws WrongArgumentException {
        try {
            return CommandType.valueOf(input.toUpperCase()).ordinal();
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            throw new WrongArgumentException(TypeOfError.UNKNOWN);
        }
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
