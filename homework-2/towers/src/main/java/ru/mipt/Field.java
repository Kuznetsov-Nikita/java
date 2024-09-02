package ru.mipt;

import java.util.ArrayList;

/**
 * Класс игровое поле, хранит информацию о текущей игровой позиции и реализует ее изменение без проверок
 */
public class Field {
    /** Размер поля */
    final public int SIZE = 8;
    /** Игровая доска, здесь расположены башни */
    Tower[][] board;

    public Field(Position position) {
        board = new Tower[SIZE][SIZE];

        for (Tower tower: position.getWhiteTowers()) {
            board[tower.getCoordinates().getX()][tower.getCoordinates().getY()] = tower;
        }
        for (Tower tower: position.getBlackTowers()) {
            board[tower.getCoordinates().getX()][tower.getCoordinates().getY()] = tower;
        }
    }

    /**
     * Удалить башню по координатам на доске
     * @param coords Координаты башни для удаления
     */
    public void deleteTower(Coordinates coords) {
        board[coords.getX()][coords.getY()].deleteTopChecker();

        if (board[coords.getX()][coords.getY()].isEmpty()) {
            board[coords.getX()][coords.getY()] = null;
        } else {
            board[coords.getX()][coords.getY()].revive();
        }
    }

    /**
     * Передвинуть башню с одной координаты на доске на другую
     * @param from Координаты, откуда передвигаем
     * @param to Координаты, куда передвигаем
     */
    public void moveTower(Coordinates from, Coordinates to) {
        Tower tower = board[from.getX()][from.getY()];
        tower.move(to);
        board[from.getX()][from.getY()] = null;
        board[to.getX()][to.getY()] = tower;

        if ((tower.getColor() == Color.WHITE && isBlackEndLine(to)) ||
            (tower.getColor() == Color.BLACK && isWhiteEndLine(to))) {
            makeKing(to);
        }
    }

    private boolean isWhiteEndLine(Coordinates coords) {
        return coords.getY() == 0;
    }

    private boolean isBlackEndLine(Coordinates coords) {
        return coords.getY() == SIZE - 1;
    }

    /**
     * Сделать башню с нужными координатами дамкой
     * @param coords Координаты башни
     */
    public void makeKing(Coordinates coords) {
        board[coords.getX()][coords.getY()].makeKing();
    }

    /**
     * Получить игровую позицию - информацию о всех башнях на доске
     * @return Текущая игровая позиция башен
     */
    public Position getPosition() {
        ArrayList<Tower> whiteTowers = new ArrayList<>();
        ArrayList<Tower> blackTowers = new ArrayList<>();

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == Color.WHITE) {
                        whiteTowers.add(board[i][j]);
                    } else {
                        blackTowers.add((board[i][j]));
                    }
                }
            }
        }

        return new Position(whiteTowers, blackTowers);
    }

    /**
     * Получить башню по ее координатам на доске
     * @param coords Координаты башни
     * @return Башня с данными координатами
     */
    public Tower getTower(Coordinates coords) {
        return board[coords.getX()][coords.getY()];
    }

    /**
     * Получить башню по ее координатам на доске
     * @param x Первая координата башни
     * @param y Вторая координата башни
     * @return Башня с данными координатами
     */
    public Tower getTower(int x, int y) {
        return board[x][y];
    }
}
