package ru.itmo.client;

public class ClientLauncher {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new ClientLauncher().getGreeting());
    }
}
