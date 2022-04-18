package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class FilterGreaterThanSpeedCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterGreaterThanSpeedCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
