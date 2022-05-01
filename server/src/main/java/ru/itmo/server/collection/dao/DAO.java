package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

public interface DAO {
    int add(HumanBeing human);
    void update(int id, HumanBeing human);
    void remove(int id);
    void removeGreater(HumanBeing humanBeing);
    HumanBeing get(int id);
    Collection<HumanBeing> getAll();
    int size();
    void setAvailableId();
    void sort();
    LocalDateTime getInitDate();
    void save() throws IOException;
    void clearCollection();
    String showCollection();
}
