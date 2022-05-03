package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

import java.util.ArrayList;
import java.util.List;

public class PrintUniqueSpeedCommand implements Command{
    private final DAO arrayDequeDAO;

    public PrintUniqueSpeedCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        arguments = null;
        List<Integer> uniqueFieldsSpeed = new ArrayList<>();
        for(HumanBeing human: arrayDequeDAO.getAll()) {
            Integer speed = human.getImpactSpeed();
            if(!uniqueFieldsSpeed.contains(speed)) {
                uniqueFieldsSpeed.add(speed);
            } else {
                uniqueFieldsSpeed.remove(speed);
            }
        }
        for(Integer element: uniqueFieldsSpeed) {
            arguments =+ element + "\n";
        }
        return arguments;
    }
}
