package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class AddCommand implements Command{
    private final DAO arrayDequeDAO;

    public AddCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
