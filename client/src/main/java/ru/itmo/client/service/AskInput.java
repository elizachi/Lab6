package ru.itmo.client.service;

import ru.itmo.client.handlers.InputHandler;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
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
     * Метод, возвращающийся к предыдущей настройке дружественного интерфейса
     */
    public static void returnFriendly() {
        friendlyInterface = CONST_FRIENDLY_INTERFACE;
    }

    /**
     * Создает новый экземпляр класса HumanBeing с пустыми полями. Проходится по полям класса, и если поле класса
     * соответствует полю, запрашиваемому в данной команде, то происходит вставка запрошенного значения
     * @param in
     */
    public Pair askInputManager(InputHandler in) throws WrongArgumentException{
        CommandType commandType = null;
        try {
            // запрос команды
            commandType = askCommand(in);
        } catch(NullPointerException e) {
            ReaderManager.returnOnPreviousReader();
            throw new WrongArgumentException(TypeOfError.END_OF_FILE);
        }
        // новый экземпляр класса HumanBeing - newHuman
        HumanBeing newHuman = new HumanBeing();
        // итератор для перемещения по нужным для команды методам
        Iterator<String> iterator = Arrays.stream(commandType.getCommandFields()).iterator();
        try {
            if(iterator.hasNext()) {
                // название нужного для запроса поля в массиве энама выбранной команды
                String commandName = iterator.next();
                // цикл foreach для полей newHuman
                for (Field fields : newHuman.getClass().getDeclaredFields()) {
                    // название нынешнего поля newHuman
                    String fieldName = fields.getName().toLowerCase();
                    // если поле массива энама команды совпадает с перебираемым полем экземпляра newHuman
                    if (fieldName.equals(commandName.substring(3).toLowerCase())) {
                        // беру ссылку на необходимый для запроса метод
                        Method method = this.getClass().getDeclaredMethod(commandName, InputHandler.class);
                        // ставлю разрешение на использование метода
                        method.setAccessible(true);
                        // вызываю нужный метод и получаю уже проверенное введенное значение
                        Object o = method.invoke(this, in);
                        // ставлю разрешение на изменение приватного поля newHuman
                        fields.setAccessible(true);
                        // изменяю значение приватного поля
                        fields.set(newHuman, o);
                        // перехожу к следующему необходимому для команды полю
                        if(iterator.hasNext()) commandName = iterator.next();
                        else break;
                    }
                }
                if(Objects.equals(commandName, "askFileName")) {
                    ReaderManager.turnOnFile(askFileName(in));
                    throw new WrongArgumentException(TypeOfError.SWITCH_READER);
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Pair(commandType.name(), newHuman);
    }
    /**
     * Запрашивает ввод команды и валидирует введённую пользователем строку
     * @param in - тип считывания (с консоли или с файла)
     * @return индекс команды, если она была найдена - иначе запрашивает повторный ввод
     */
    private CommandType askCommand(InputHandler in) {
        CommandType input = null;
        while(input == null) {
            printMessage("Введите команду:");
            try {
                input = isCorrectCommand(in.readInput());
            } catch (IOException e) {

            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                input = null;
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
            printMessage("Есть ли у него зубочистка?");
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

    private Mood askMood(InputHandler in) {
        Mood mood = null;
        boolean flag = true;
        while (flag) {
            printMessage("Введите состояние персонажа:");
            try {
                mood = isCorrectMood(in.readInput());
                flag = false;
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                flag = true;
            } catch (IOException e) {

            }
        }
        return mood;
    }

    private Car askCar(InputHandler in) {
        printMessage("Введите данные о машине персонажа.");
        String carName = null;
        boolean flag = true;
        while(flag) {
            printMessage("Введите название машины:");
            try {
                carName = in.readInput();
                if(carName.isEmpty()) msg.printWarningMessage();
                flag = false;
            } catch (IOException e) {

            }
        }
        boolean cool = false;
        flag = true;
        while(flag) {
            printMessage("Машина крутая?");
            try {
                cool = toBoolean(in.readInput(), false);
                flag = false;
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
                flag = true;
            } catch (IOException e) {

            }
        }
        return new Car(carName, cool);
    }

    private BufferedReader askFileName(InputHandler in) {
        FileReader fileInput = null;
        while(fileInput == null) {
            printMessage("Введите путь до файла, который хотите прочесть:");
            try {
                fileInput = isCorrectFile(in.readInput());
            } catch (IOException e) {
//                ReaderManager.returnOnPreviousReader();
//                throw new EndException("Произошла ошибка, невозможно прочитать данные из файла.\n");
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            }
        }
        return new BufferedReader(fileInput);
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

    private CommandType isCorrectCommand(String input) throws WrongArgumentException {
        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            throw new WrongArgumentException(TypeOfError.UNKNOWN);
        }
    }

    private Mood isCorrectMood(String input) throws WrongArgumentException {
        try {
            return Mood.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) {
                msg.printWarningMessage();
                return null;
            }
            else throw new WrongArgumentException(TypeOfError.UNKNOWN);
        }
    }

    private FileReader isCorrectFile(String input) throws WrongArgumentException{
        try {
            return new FileReader(input);
        } catch (FileNotFoundException e) {
            throw new WrongArgumentException(TypeOfError.NOT_FOUND);
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
enum CommandType {
    ADD(new String[]{"askName", "askSoundtrackName", "askMinutesOfWaiting",
            "askImpactSpeed", "askRealHero", "askHasToothpick", "askCoordinates", "askMood", "askCar"}),
    CLEAR(new String[]{}),
    EXECUTE_SCRIPT(new String[]{"askFileName"}),
    EXIT(new String[]{}),
    FILTER_BY_MINUTES_OF_WAITING(new String[]{"askMinutesOfWaiting"}),
    FILTER_GREATER_THAN_IMPACT_SPEED(new String[]{"askImpactSpeed"}),
    HEAD(new String[]{}),
    HELP(new String[]{}),
    INFO(new String[]{}),
    PRINT_UNIQUE_IMPACT_SPEED(new String[]{}),
    REMOVE_BY_ID(new String[]{"askId"}),
    REMOVE_GREATER(new String[]{"askName", "askSoundtrackName", "askMinutesOfWaiting",
            "askImpactSpeed", "askRealHero", "askHasToothpick", "askCoordinates", "askMood", "askCar"}),
    REMOVE_HEAD(new String[]{}),
    SHOW(new String[]{}),
    UPDATE(new String[]{"askId", "askName", "askSoundtrackName", "askMinutesOfWaiting",
            "askImpactSpeed", "askRealHero", "askHasToothpick", "askCoordinates", "askMood", "askCar"});

    private final String[] commandFields;

    CommandType(String[] fields) {
        this.commandFields = fields;
    }

    public String[] getCommandFields() {
        return this.commandFields;
    }
};
