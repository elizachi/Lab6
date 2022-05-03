package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class RemoveHeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveHeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        arguments = (arrayDequeDAO.getHead());
        if (!(arrayDequeDAO.getHead() == null)) {
            arrayDequeDAO.remove(arrayDequeDAO.getHead().getId());
        } else {
            arguments = ("Коллекция пустая.");
        }
        return arguments;
    }
}
