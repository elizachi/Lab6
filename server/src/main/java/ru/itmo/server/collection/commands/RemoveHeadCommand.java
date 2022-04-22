package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class RemoveHeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveHeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
