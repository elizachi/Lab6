package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class AddCommand implements Command{
    private final DAO arrayDequeDAO;

    public AddCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        arrayDequeDAO.add(humanBeing);
        arrayDequeDAO.sort();
    }
}
