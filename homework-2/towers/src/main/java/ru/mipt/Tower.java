package ru.mipt;

import java.util.ArrayDeque;

/**
 * Класс башня
 */
public class Tower {
    /** Координаты башни */
    private Coordinates coords;
    /** Шашки, входящие в башню */
    private ArrayDeque<Checker> checkers;
    /** Флаг, была ли побита башня на текущем ходу */
    private boolean isKilled = false;

    public Tower(Coordinates coords, ArrayDeque<Checker> checkers) {
        this.coords = coords;
        this.checkers = checkers;
    }

    private Checker topChecker() {
        return checkers.getFirst();
    }

    public Color getColor() {
        return topChecker().getColor();
    }

    public boolean isKing() {
        return topChecker().isKing();
    }

    public Coordinates getCoordinates() {
        return coords;
    }

    public void move(Coordinates coords) {
        this.coords = coords;
    }

    public void makeKing() {
        topChecker().makeKing();
    }

    public void kill() {
        isKilled = true;
    }

    public void revive() {
        isKilled = false;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void addChecker(Checker checker) {
        checkers.addLast(checker);
    }

    public Checker takeTopChecker() {
        return checkers.getFirst();
    }

    public void deleteTopChecker() {
        checkers.removeFirst();
    }

    public boolean isEmpty() {
        return checkers.size() == 0;
    }

    /**
     * Получить строковое представление башни
     * @return Строка, описывающая башню
     */
    public String toString() {
        StringBuilder stringTower = new StringBuilder();

        stringTower.append(coords.toString());
        stringTower.append('_');

        for (Checker checker: checkers) {
            if (checker.getColor() == Color.WHITE) {
                stringTower.append((checker.isKing()) ? "W" : "w");
            } else {
                stringTower.append((checker.isKing()) ? "B" : "b");
            }
        }

        return stringTower.toString();
    }
}
