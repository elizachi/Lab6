package ru.itmo.common.collection.commands;

import ru.itmo.common.collection.dao.DAO;

public class FilterByMinutesCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterByMinutesCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute() {

    }
}
