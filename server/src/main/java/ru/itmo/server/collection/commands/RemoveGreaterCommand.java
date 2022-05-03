package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class RemoveGreaterCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveGreaterCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        if (!(arrayDequeDAO.size() == 0)) {
            arrayDequeDAO.removeGreater(humanBeing);
            return null;
        } else {
            return ("Коллекция пуста.");
        }
    }
}
