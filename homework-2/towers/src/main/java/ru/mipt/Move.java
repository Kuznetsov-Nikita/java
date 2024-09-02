package ru.mipt;

import java.util.ArrayList;

/**
 * Класс ход для игры башни
 */
public class Move {
    /** Цвет игрока, чей это ход */
    final private Color playerColor;
    /** Флаг, является ли этот ход бьющим */
    final private boolean isTaking;
    /** Координаты хода - массив координат от начальной до конечной, как двигается ходящая башня */
    final private ArrayList<Coordinates> moveCoords;
    /** Следующая неполученная из хода координата башни */
    private int currentMoveCoordIndex;

    public Move(Color playerColor, boolean isTaking, ArrayList<Coordinates> moveCoords) {
        this.playerColor = playerColor;
        this.isTaking = isTaking;
        this.moveCoords = moveCoords;
        currentMoveCoordIndex = 0;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public boolean isTaking() {
        return isTaking;
    }

    public ArrayList<Coordinates> getMoveCoords() {
        return moveCoords;
    }

    /**
     * Получить следующую координату, на которую двигается башня за этот ход
     * @return Следующая координата башни
     */
    public Coordinates nextCoord() {
        return moveCoords.get(currentMoveCoordIndex++);
    }

    /**
     * Проверить, есть ли неполученная еще следующая координата башни
     * @return Флаг, есть ли неполученные координаты
     */
    public boolean hasNextCoord() {
        return currentMoveCoordIndex < moveCoords.size();
    }
}
