package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.util.Collection;

public interface DAO {
    int add(HumanBeing human);
    void update(int id, HumanBeing human);
    void remove(int id);
    HumanBeing get(int id);
    Collection<HumanBeing> getAll();
}
