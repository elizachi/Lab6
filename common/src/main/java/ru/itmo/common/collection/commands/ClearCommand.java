package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class ClearCommand implements Command{
    private final DAO arrayDequeDAO;

    public ClearCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
