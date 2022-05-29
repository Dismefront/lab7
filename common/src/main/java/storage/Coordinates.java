package storage;

import java.io.Serializable;

public class Coordinates implements Validateable, Serializable {
    private Float x; //Максимальное значение поля: 46, Поле не может быть null
    private Integer y; //Поле не может быть null

    public Coordinates(float x, int y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public static boolean validateX(Float x) {
        return x != null && x <= 46;
    }

    public static boolean validateY(Integer x) {
        return x != null;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean isValid() {
        return x != null && x <= 46 && y != null && !Float.isInfinite(x) && !Float.isNaN(x);
    }
}
