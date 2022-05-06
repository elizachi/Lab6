package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class FilterByMinutesCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterByMinutesCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        return arrayDequeDAO.filterByMinutes(humanBeing.getMinutesOfWaiting()).toString();
    }
}
