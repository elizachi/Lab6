package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class FilterByMinutesCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterByMinutesCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {

    }
}
