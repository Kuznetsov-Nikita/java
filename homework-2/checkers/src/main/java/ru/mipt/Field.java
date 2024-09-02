package ru.mipt;

import java.util.ArrayList;

/**
 * Класс игровое поле, хранит информацию о текущей игровой позиции и реализует ее изменение без проверок
 */
public class Field {
    /** Размер поля */
    final public int SIZE = 8;
    /** Игровая доска, здесь расположены шашки */
    Checker[][] board;

    public Field(Position position) {
        board = new Checker[SIZE][SIZE];

        for (Checker checker: position.getWhiteCheckers()) {
            board[checker.getCoordinates().getX()][checker.getCoordinates().getY()] = checker;
        }
        for (Checker checker: position.getBlackCheckers()) {
            board[checker.getCoordinates().getX()][checker.getCoordinates().getY()] = checker;
        }
    }

    /**
     * Удалить шашку по координатам на доске
     * @param coords Координаты шашки для удаления
     */
    public void deleteChecker(Coordinates coords) {
        board[coords.getX()][coords.getY()] = null;
    }

    /**
     * Передвинуть шашку с одной координаты на доске на другую
     * @param from Координаты, откуда передвигаем
     * @param to Координаты, куда передвигаем
     */
    public void moveChecker(Coordinates from, Coordinates to) {
        Checker checker = board[from.getX()][from.getY()];
        checker.move(to);
        board[from.getX()][from.getY()] = null;
        board[to.getX()][to.getY()] = checker;

        if ((checker.getColor() == Color.WHITE && isBlackEndLine(to)) ||
            (checker.getColor() == Color.BLACK && isWhiteEndLine(to))) {
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
     * Сделать шашку с нужными координатами дамкой
     * @param coords Координаты шашки
     */
    public void makeKing(Coordinates coords) {
        board[coords.getX()][coords.getY()].makeKing();
    }

    /**
     * Получить игровую позицию - информацию о всех шашках на доске
     * @return Текущая игровая позиция шашек
     */
    public Position getPosition() {
        ArrayList<Checker> whiteCheckers = new ArrayList<>();
        ArrayList<Checker> blackCheckers = new ArrayList<>();

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && board[i][j].isKing()) {
                    if (board[i][j].getColor() == Color.WHITE) {
                        whiteCheckers.add(board[i][j]);
                    } else {
                        blackCheckers.add((board[i][j]));
                    }
                }
            }
        }
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && !board[i][j].isKing()) {
                    if (board[i][j].getColor() == Color.WHITE) {
                        whiteCheckers.add(board[i][j]);
                    } else {
                        blackCheckers.add((board[i][j]));
                    }
                }
            }
        }

        return new Position(whiteCheckers, blackCheckers);
    }

    /**
     * Получить шашку по ее координатам на доске
     * @param coords Координаты шашки
     * @return Шашка с данными координатами
     */
    public Checker getChecker(Coordinates coords) {
        return board[coords.getX()][coords.getY()];
    }

    /**
     * Получить шашку по ее координатам на доске
     * @param x Первая координата шашки
     * @param y Вторая координата шашки
     * @return Шашка с данными координатами
     */
    public Checker getChecker(int x, int y) {
        return board[x][y];
    }
}
