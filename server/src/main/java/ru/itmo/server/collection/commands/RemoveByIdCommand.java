package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class RemoveByIdCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveByIdCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
