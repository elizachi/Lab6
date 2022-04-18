package ru.itmo.client.handlers;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputHandler extends InputHandler{
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Переопределённый метод для считывания с консоли
     * @return считанная строка без лишних пробелов
     */
    @Override
    public String readInput() {
        //ВНиМАНиЕ!!!! Считывает только первое слово введённой строки. Остальные данные игнорируются.
        try {
            String input = scanner.nextLine().trim().split(" ")[0];
            return input;
        } catch(NoSuchElementException e) {
            System.out.print("Куда ты жмал?? Программа завершает свою работу без сохранения данных.\n");
            System.exit(0);
        }
        return null;
    }
}
