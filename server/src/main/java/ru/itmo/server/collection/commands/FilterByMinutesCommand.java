package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class FilterByMinutesCommand implements Command{
    private final DAO arrayDequeDAO;

    public FilterByMinutesCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        System.out.println(arrayDequeDAO.filterByMinutes(humanBeing.getMinutesOfWaiting()));
    }
}
