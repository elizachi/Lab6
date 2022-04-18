package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class ShowCommand implements Command{
    private final DAO arrayDequeDAO;

    public ShowCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
