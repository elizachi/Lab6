package ru.itmo.common.model;

public class Pair {
    private final String command;
    private final HumanBeing human;

    public Pair(String command, HumanBeing human) {
        this.command = command;
        this.human = human;
    }
    public String first() {
        return command;
    }
    public HumanBeing second() {
        return human;
    }
}
