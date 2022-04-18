package ru.itmo.common.collection.dao;

import ru.itmo.common.collection.model.HumanBeing;

import java.util.Collection;

public interface DAO {
    int add(HumanBeing human);
    void update(int id, HumanBeing human);
    void remove(int id);
    HumanBeing get(int id);
    Collection<HumanBeing> getAll();
}
