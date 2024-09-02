package ru.mipt;

/**
 * Класс шашка
 */
public class Checker {
    /** Координаты шашки */
    private Coordinates coords;
    /** Цвет шашки */
    final private Color color;
    /** Флаг, является ли шашка дамкой */
    private boolean isKing;
    /** Флаг, срублена ли шашка на текущем ходу */
    private boolean isKilled = false;

    public Checker(Coordinates coords, Color color, boolean isKing) {
        this.coords = coords;
        this.color = color;
        this.isKing = isKing;
    }

    public Color getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public Coordinates getCoordinates() {
        return coords;
    }

    public void move(Coordinates coords) {
        this.coords = coords;
    }

    public void makeKing() {
        isKing = true;
    }

    public void kill() {
        isKilled = true;
    }

    public boolean isKilled() {
        return isKilled;
    }
}
