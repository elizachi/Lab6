package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class InfoCommand implements Command{
    private final DAO arrayDequeDAO;

    public InfoCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
