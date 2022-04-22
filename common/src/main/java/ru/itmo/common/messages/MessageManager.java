package ru.itmo.common.messages;

import ru.itmo.common.exceptions.AbstractException;

import java.util.Scanner;

public class MessageManager {
    Scanner scanner = new Scanner(System.in);

    public String askFriendly() {
        System.out.println("Включить дружественный интерфейс?");
        return scanner.nextLine().trim()
                .split(" ")[0]
                .toLowerCase();
    }

    public void printErrorMessage(AbstractException e) {
        System.err.println(e.getType().getDescription());
    }

    public void specialMessage(String field) {
        System.out.println("Введите"+field+":");
    }
}