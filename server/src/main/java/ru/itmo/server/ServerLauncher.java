package ru.itmo.server;

public class ServerLauncher {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new ServerLauncher().getGreeting());
    }
}
