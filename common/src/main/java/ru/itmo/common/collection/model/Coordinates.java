package ru.itmo.common.collection.model;

public class Coordinates {
    private int x;
    private Float y; //Значение поля должно быть больше -188, Поле не может быть null

    public Coordinates(){}

    public Coordinates(int x, Float y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates (" +
                "x = " + x +
                ", y = " + y +
                ')';
    }
}