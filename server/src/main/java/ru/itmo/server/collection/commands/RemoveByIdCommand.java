package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class RemoveByIdCommand implements Command{
    private final DAO arrayDequeDAO;

    public RemoveByIdCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        arrayDequeDAO.remove(humanBeing.getId());
    }
}
