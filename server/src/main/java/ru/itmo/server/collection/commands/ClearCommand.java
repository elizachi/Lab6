package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class ClearCommand implements Command{
    private final DAO arrayDequeDAO;

    public ClearCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}