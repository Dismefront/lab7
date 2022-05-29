package storage;

import java.io.Serializable;

public class Person implements Validateable, Serializable {
    private int weight; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null

    public Person(int weight, Color eyeColor, Color hairColor, Country nationality) {
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "(weight:" + this.weight +
                ", eyeColor: " + this.eyeColor +
                ", hairColor: " + this.hairColor +
                ", nationality: " + this.nationality +
                ")";
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public int getWeight() {
        return weight;
    }

    public Country getNationality() {
        return nationality;
    }

    @Override
    public boolean isValid() {
        return weight > 0;
    }

}
