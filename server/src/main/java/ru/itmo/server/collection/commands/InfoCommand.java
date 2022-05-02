package ru.itmo.server.collection.commands;

import ru.itmo.server.collection.dao.DAO;

public class InfoCommand implements Command{
    private final DAO arrayDequeDAO;

    public InfoCommand(DAO arrayDequeDAO) {
        this.arrayDequeDAO = arrayDequeDAO;
    }

    @Override
    public void execute(Object arguments) {
        System.out.print("info: Коллекция ArrayDeque,\n" + "Количество элементов: " + arrayDequeDAO.size() + "\n");
    }
}
