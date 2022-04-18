package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class HeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public HeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
