package ru.itmo.server.collection.dao;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.utility.FileManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayDequeDAO implements DAO{
    private ArrayDeque<HumanBeing> humanCollection = new ArrayDeque<>();
    private final FileManager fileManager = new FileManager("DAO_COLLECTION_FILEPATH");
    private static int availableId = 0;
    private LocalDateTime initDate;

    public ArrayDequeDAO(ArrayDeque<HumanBeing> humanCollection) {
        this.humanCollection = humanCollection;
        initDate = LocalDateTime.now();
        setAvailableId();
    }

    /**
     * getters and setters
     */
    public LocalDateTime getInitDate(){
        return initDate;
    }
    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }
    public ArrayDeque<HumanBeing> getHumanCollection() {
        return humanCollection;
    }
    public void setHumanCollection(ArrayDeque<HumanBeing> humanCollection) {
        this.humanCollection = humanCollection;
    }

    /**
     * override DAO methods
     */
    @Override
    public void clearCollection() {
        humanCollection.clear();
    }

    @Override
    public int add(HumanBeing human) {
        humanCollection.add(human);
        human.setId(availableId);
        human.setCreationDate(LocalDate.now());
        return availableId++;
    }

    @Override
    public void update(int id, HumanBeing updatedHuman) {
        HumanBeing existedHuman = get(id);
        if(existedHuman != null) {
            existedHuman.setName(updatedHuman.getName());
            existedHuman.setSoundtrackName(updatedHuman.getSoundtrackName());
            existedHuman.setMinutesOfWaiting(updatedHuman.getMinutesOfWaiting());
            existedHuman.setImpactSpeed(updatedHuman.getImpactSpeed());
            existedHuman.setRealHero(updatedHuman.isRealHero());
            existedHuman.setHasToothpick(updatedHuman.isHasToothpick());
            existedHuman.setCoordinates(updatedHuman.getCoordinates());
            existedHuman.setMood(updatedHuman.getMood());
            existedHuman.setCar(updatedHuman.getCar());
        }
    }

    @Override
    public List<?> filterGreaterThanSpeed(int speed){
        return humanCollection.stream().filter(humanBeing -> humanBeing.getImpactSpeed() > speed).collect(Collectors.toList());
    }

    @Override
    public List<?> filterByMinutes(Long minutesOfWaiting) {
        return humanCollection.stream().filter(humanBeing -> Objects.equals(humanBeing.getMinutesOfWaiting(), minutesOfWaiting)).collect(Collectors.toList());
    }

    @Override
    public void remove(int id) {
        HumanBeing existedHuman = get(id);
        if(existedHuman != null) {
            humanCollection.remove(existedHuman);
        }
    }

    @Override
    public void removeGreater(HumanBeing humanBeing) {
        humanCollection.removeIf(humanBeing1 -> humanBeing1.compareTo(humanBeing) > 0);
    }

    @Override
    public HumanBeing get(int id) {
        return humanCollection.stream().filter(humanBeing -> humanBeing.getId() == id).findFirst().orElse(null);
    }

    @Override
    public HumanBeing getHead(){
        return humanCollection.stream().findFirst().orElse(null);
    }

    @Override
    public Collection<HumanBeing> getAll() {
        return humanCollection;
    }

    @Override
    public int size() {
        return humanCollection.size();
    }

    @Override
    public void setAvailableId() {
        int id;
        if (humanCollection.isEmpty()) {
            id = 0;
        } else {
            id = getMaxId();
        }
        availableId = id + 1;
    }

    public int getMaxId(){
        ArrayDeque<HumanBeing> cloneCollection = humanCollection.clone();
        Integer[] ids = new Integer[cloneCollection.size()];
        int i = 0;
        int max;
        while (!(cloneCollection.isEmpty())) {
            ids[i] = cloneCollection.poll().getId();
            i++;
        }
        max = Collections.max(Arrays.asList(ids));
        return max;
    }

    @Override
    public void save() {
        fileManager.writeCollection(humanCollection);
    }

    @Override
    public String showCollection(){
        if (humanCollection.isEmpty()) return null;
        return humanCollection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    @Override
    public void sort() {
        HumanBeing[] humanBeingArray = humanCollection.toArray(new HumanBeing[0]);
        Arrays.sort(humanBeingArray, new ru.itmo.server.utility.HumanComparator());
        humanCollection.clear();
        humanCollection.addAll(Arrays.asList(humanBeingArray));
    }
}
