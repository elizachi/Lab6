package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class ShowCommand implements Command{
    private final DAO arrayDequeDAO;

    public ShowCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {
        System.out.println(arrayDequeDAO.showCollection());
    }
}
