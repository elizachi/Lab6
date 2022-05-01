package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class PrintUniqueSpeedCommand implements Command{
    private final DAO arrayDequeDAO;

    public PrintUniqueSpeedCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {

    }
}
