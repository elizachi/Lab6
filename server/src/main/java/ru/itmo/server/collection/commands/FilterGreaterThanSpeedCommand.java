package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class FilterGreaterThanSpeedCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterGreaterThanSpeedCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        return arrayDequeDAO.filterGreaterThanSpeed(humanBeing.getImpactSpeed()).toString();
    }
}
