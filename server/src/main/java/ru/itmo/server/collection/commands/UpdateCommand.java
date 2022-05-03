package ru.itmo.server.collection.commands;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.collection.dao.DAO;

public class UpdateCommand implements Command{
    private final DAO arrayDequeDAO;

    public UpdateCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public Object execute(Object arguments) {
        HumanBeing humanBeing = (HumanBeing) arguments;
        if(arrayDequeDAO.get(humanBeing.getId()) != null) {
            arguments = null;
            arrayDequeDAO.update(humanBeing.getId(), humanBeing);
            arrayDequeDAO.sort();
        } else {
            arguments = ("Элемента с таким id не нашлось.\n");
        }
        return arguments;
    }
}
