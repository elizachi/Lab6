package ru.itmo.common.messages;

import java.util.Scanner;

public class MessageManager {
    Scanner scanner = new Scanner(System.in);

    public String askFriendly() {
        System.out.println("Включить дружественный интерфейс?");
        return scanner.nextLine().trim()
                .split(" ")[0]
                .toLowerCase();
    }

    public void printErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }

    public void specialMessage(String field) {
        System.out.println("Введите"+field+":");
    }
}