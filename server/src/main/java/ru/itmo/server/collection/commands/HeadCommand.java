package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class HeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public HeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
