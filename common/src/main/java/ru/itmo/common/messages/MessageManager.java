package ru.itmo.common.messages;

import ru.itmo.common.exceptions.WrongArgumentException;

import java.util.Scanner;

public class MessageManager {
    Scanner scanner = new Scanner(System.in);

    public String askFriendly() {
        System.out.println("Включить дружественный интерфейс?");
        return scanner.nextLine().trim()
                .split(" ")[0]
                .toLowerCase();
    }

    public void printErrorMessage(WrongArgumentException e) {
        System.err.println(e.getType().getDescription());
    }

    public void printWarningMessage() {
        System.out.println("\u001B[33mВы ввели пустую строку. Поле примет значение null.\u001B[0m");
    }
    public void printWarningMessage(WrongArgumentException e) {
        System.out.println("\u001B[33m"+e.getType().getDescription()+"\u001B[0m");
    }
    public void specialMessage(String field) {
        System.out.println("Введите"+field+":");
    }
}