package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.util.ArrayDeque;
import java.util.Collection;

public class ArrayDequeDAO implements DAO{
    private ArrayDeque<HumanBeing> humanCollection = new ArrayDeque<>();
    @Override
    public int add(HumanBeing human) {
        return 0;
    }

    @Override
    public void update(int id, HumanBeing human) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public HumanBeing get(int id) {
        return null;
    }

    @Override
    public Collection<HumanBeing> getAll() {
        return null;
    }
}
