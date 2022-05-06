package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class RemoveHeadCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveHeadCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        if (arrayDequeDAO.size() != 0) {
            String human = arrayDequeDAO.getHead().toString();
            arrayDequeDAO.remove(arrayDequeDAO.getHead().getId());
            return human;
        } else {
            return "Коллекция пустая.";
        }
    }
}
