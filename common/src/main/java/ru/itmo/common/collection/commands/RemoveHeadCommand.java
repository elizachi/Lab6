package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class RemoveHeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveHeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
