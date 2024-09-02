package ru.mipt;

import java.util.ArrayList;

/**
 * Класс игровая позиция - информация о всех шашках
 */
public class Position {
    /** Белые шашки */
    final private ArrayList<Checker> whiteCheckers;
    /** Черные шашки */
    final private ArrayList<Checker> blackCheckers;

    public Position(ArrayList<Checker> whiteCheckers, ArrayList<Checker> blackCheckers) {
        this.whiteCheckers = whiteCheckers;
        this.blackCheckers = blackCheckers;
    }

    public ArrayList<Checker> getWhiteCheckers() {
        return whiteCheckers;
    }

    public ArrayList<Checker> getBlackCheckers() {
        return blackCheckers;
    }

    /**
     * Преобразовать в строковое представление
     * @return Строковое представление позиции
     */
    public String toString() {
        StringBuilder stringPosition = new StringBuilder();

        for (Checker checker: whiteCheckers) {
            if (checker.isKing()) {
                stringPosition.append(checker.getCoordinates().toString().toUpperCase());
            } else {
                stringPosition.append(checker.getCoordinates().toString());
            }
            stringPosition.append(' ');
        }
        stringPosition.insert(stringPosition.length(), '\n');

        for (Checker checker: blackCheckers) {
            if (checker.isKing()) {
                stringPosition.append(checker.getCoordinates().toString().toUpperCase());
            } else {
                stringPosition.append(checker.getCoordinates().toString());
            }
            stringPosition.append(' ');
        }

        return stringPosition.toString();
    }
}
