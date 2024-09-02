package ru.mipt;

import java.util.ArrayList;

/**
 * Класс игровая позиция - информация о всех башнях
 */
public class Position {
    /** Белые шашки */
    final private ArrayList<Tower> whiteTowers;
    /** Черные шашки */
    final private ArrayList<Tower> blackTowers;

    public Position(ArrayList<Tower> whiteTowers, ArrayList<Tower> blackTowers) {
        this.whiteTowers = whiteTowers;
        this.blackTowers = blackTowers;
    }

    public ArrayList<Tower> getWhiteTowers() {
        return whiteTowers;
    }

    public ArrayList<Tower> getBlackTowers() {
        return blackTowers;
    }

    /**
     * Преобразовать в строковое представление
     * @return Строковое представление позиции
     */
    public String toString() {
        StringBuilder stringPosition = new StringBuilder();

        for (Tower tower: whiteTowers) {
            stringPosition.append(tower.toString());
            stringPosition.append(' ');
        }
        stringPosition.insert(stringPosition.length(), '\n');

        for (Tower tower: blackTowers) {
            stringPosition.append(tower.toString());
            stringPosition.append(' ');
        }

        return stringPosition.toString();
    }
}
