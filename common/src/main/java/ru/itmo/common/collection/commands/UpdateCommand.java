package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class UpdateCommand implements Command{
    private final DAO arrayDequeDAO;

    public UpdateCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
