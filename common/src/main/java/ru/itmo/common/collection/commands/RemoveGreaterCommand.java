package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class RemoveGreaterCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveGreaterCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
