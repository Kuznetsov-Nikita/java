package ru.mipt;

import java.util.ArrayList;

/**
 * Класс состояния игры, хранит состояние игры и реализует логику ходов
 */
public class GameState {
    /** Поле для игры */
    private Field field;
    /** Цвет игрока, который должен ходить сейчас */
    private Color playerColor;

    public GameState(Position position) {
        field = new Field(position);
        playerColor = Color.WHITE;
    }

    public Position getPosition() {
        return field.getPosition();
    }

    /**
     * Метод сделать ход
     * @param move Ход, который необходимо сделать
     * @throws GeneralError Любая ошибка, которая может произойти в процессе попытки сделать указанный ход
     */
    public void makeMove(Move move) throws GeneralError {
        ArrayList<Coordinates> moveCoords = move.getMoveCoords();
        Coordinates from = moveCoords.get(0);
        Checker movingChecker = field.getChecker(from);

        if (!isBlackCell(from)) {
            throw new GeneralError("general error");
        }
        if (!isBusyCell(from)) {
            throw new GeneralError("general error");
        }
        if (movingChecker.getColor() != playerColor) {
            throw new GeneralError("general error");
        }

        if (!move.isTaking()) {
            quiteMove(move);
        } else {
            takingMove(move);
        }
        playerColor = (playerColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void quiteMove(Move move) throws GeneralError {
        ArrayList<Coordinates> moveCoords = move.getMoveCoords();
        Coordinates from = moveCoords.get(0);
        Coordinates to = moveCoords.get(1);
        Checker movingChecker = field.getChecker(from);

        if (isBusyCell(to)) {
            throw new BusyCellException("busy cell");
        }
        if (!isBlackCell(to)) {
            throw new WhiteCellException("white cell");
        }
        if (hasTakingMove(move.getPlayerColor())) {
            throw new InvalidMoveException("invalid move");
        }
        if (!isOnOneDiagonal(from, to)) {
            throw new GeneralError("general error");
        }
        if (!movingChecker.isKing() && !isNearby(from, to)) {
            throw new GeneralError("general error");
        }
        if (movingChecker.isKing()) {
            int maxShift = Math.abs(to.getX() - from.getX());
            int x_offset = (to.getX() - from.getX()) / Math.abs(to.getX() - from.getX());
            int y_offset = (to.getY() - from.getY()) / Math.abs(to.getY() - from.getY());

            for (int shift = 1; shift < maxShift; ++shift) {
                Checker currentCellChecker = field.getChecker(from.getX() + shift * x_offset, from.getY() + shift * y_offset);
                if (currentCellChecker != null) {
                    throw new GeneralError("general error");
                }
            }
        }

        field.moveChecker(from, to);
    }

    private boolean isBlackCell(Coordinates coords) {
        return (coords.getX() + coords.getY()) % 2 == 0;
    }

    private boolean isBusyCell(Coordinates coords) {
        return field.getChecker(coords) != null;
    }

    private boolean isOnOneDiagonal(Coordinates coords1, Coordinates coords2) {
        return ((coords2.getX() - coords1.getX()) == (coords2.getY() - coords1.getY())) ||
                ((coords2.getX() - coords1.getX()) == (coords1.getY() - coords2.getY()));
    }

    private boolean isNearby(Coordinates coords1, Coordinates coords2) {
        return ((Math.abs(coords1.getX() - coords2.getX()) == 1) && (Math.abs(coords1.getY()- coords2.getY()) == 1));
    }

    private void takingMove(Move move) throws GeneralError {
        Checker movingChecker = field.getChecker(move.nextCoord());

        while (move.hasNextCoord()) {
            Coordinates nextCoords = move.nextCoord();
            take(movingChecker, nextCoords);
        }

        if (hasTakingMoveChecker(movingChecker)) {
            throw new InvalidMoveException("invalid move");
        }

        for (int i = 0; i < field.SIZE; ++i) {
            for (int j = 0; j < field.SIZE; ++j) {
                if (field.getChecker(i, j) != null && field.getChecker(i, j).isKilled()) {
                    field.deleteChecker(new Coordinates(i, j));
                }
            }
        }
    }

    private void take(Checker movingChecker, Coordinates to) throws GeneralError {
        Coordinates from = movingChecker.getCoordinates();

        if (isBusyCell(to)) {
            throw new BusyCellException("busy cell");
        }
        if (!isBlackCell(to)) {
            throw new WhiteCellException("white cell");
        }
        if (!isOnOneDiagonal(from, to)) {
            throw new GeneralError("general error");
        }

        if (!movingChecker.isKing()) {
            if (!isTakingNearby(from, to)) {
                throw new GeneralError("general error");
            }

            Coordinates underTakingCheckerCoords = new Coordinates((from.getX() + to.getX()) / 2, (from.getY() + to.getY()) / 2);
            if (field.getChecker(underTakingCheckerCoords) == null) {
                throw new GeneralError("general error");
            }
            if (field.getChecker(underTakingCheckerCoords).getColor() == movingChecker.getColor() || field.getChecker(underTakingCheckerCoords).isKilled()) {
                throw new GeneralError("general error");
            }

            field.getChecker(underTakingCheckerCoords).kill();
            field.moveChecker(from, to);
        } else {
            int maxShift = Math.abs(to.getX() - from.getX());
            int x_offset = (to.getX() - from.getX()) / Math.abs(to.getX() - from.getX());
            int y_offset = (to.getY() - from.getY()) / Math.abs(to.getY() - from.getY());

            Checker underTakingChecker = null;
            int underTakingCheckersCount = 0;

            for (int shift = 1; shift < maxShift; ++shift) {
                Checker currentCellChecker = field.getChecker(from.getX() + shift * x_offset, from.getY() + shift * y_offset);
                if (currentCellChecker != null &&
                    (currentCellChecker.getColor() == movingChecker.getColor() || currentCellChecker.isKilled())) {
                    throw new GeneralError("general error");
                }
                if (currentCellChecker != null &&
                    currentCellChecker.getColor() != movingChecker.getColor()) {
                    ++underTakingCheckersCount;
                    underTakingChecker = currentCellChecker;
                }
            }

            if (underTakingCheckersCount != 1) {
                throw new GeneralError("general error");
            }

            underTakingChecker.kill();
            field.moveChecker(from, to);
        }
    }

    private boolean isTakingNearby(Coordinates coords1, Coordinates coords2) {
        return ((Math.abs(coords1.getX() - coords2.getX()) == 2) && (Math.abs(coords1.getY()- coords2.getY()) == 2));
    }

    private boolean hasTakingMove(Color playerColor) {
        ArrayList<Checker> playerCheckers;
        if (playerColor == Color.WHITE) {
            playerCheckers = field.getPosition().getWhiteCheckers();
        } else {
            playerCheckers = field.getPosition().getBlackCheckers();
        }

        boolean answer = false;

        for (Checker checker: playerCheckers) {
            if (hasTakingMoveChecker(checker)) {
                answer = true;
                break;
            }
        }

        return answer;
    }

    private boolean hasTakingMoveChecker(Checker checker) {
        int[][] offset = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        Coordinates coords = checker.getCoordinates();
        boolean answer = false;

        if (!checker.isKing()) {
            for (int i = 0; i < offset.length; ++i) {
                int currentToX = coords.getX() + 2 * offset[i][0];
                int currentToY = coords.getY() + 2 * offset[i][1];

                if (currentToX < field.SIZE && currentToY < field.SIZE && currentToX >= 0 && currentToY >= 0) {
                    Checker currentToChecker = field.getChecker(currentToX, currentToY);
                    Checker currentCellChecker = field.getChecker(coords.getX() + offset[i][0], coords.getY() + offset[i][1]);
                    if (currentToChecker == null && currentCellChecker != null &&
                        currentCellChecker.getColor() != checker.getColor() && !currentCellChecker.isKilled()) {
                        answer = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < offset.length; ++i) {
                int x_offset = offset[i][0];
                int y_offset = offset[i][1];

                for (int shift = 1; shift < field.SIZE; ++shift) {
                    int currentToX = coords.getX() + shift * x_offset;
                    int currentToY = coords.getY() + shift * y_offset;

                    if (currentToX < field.SIZE && currentToY < field.SIZE && currentToX >= 0 && currentToY >= 0) {
                        Checker currentToChecker = field.getChecker(currentToX, currentToY);
                        
                        if (currentToChecker != null && currentToChecker.getColor() == checker.getColor()) {
                            break;
                        }
                        if (currentToChecker != null && currentToChecker.getColor() != checker.getColor()) {
                            int nextToX = coords.getX() + (shift + 1) * x_offset;
                            int nextToY = coords.getY() + (shift + 1) * y_offset;

                            if (nextToX < field.SIZE && nextToY < field.SIZE && nextToX >= 0 && nextToY >= 0 &&
                                field.getChecker(nextToX, nextToY) == null && !currentToChecker.isKilled()) {
                                answer = true;
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }

                if (answer) {
                    break;
                }
            }
        }

        return answer;
    }
}
