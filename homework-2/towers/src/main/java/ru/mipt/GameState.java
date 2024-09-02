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
        Tower movingTower = field.getTower(from);

        if (!isBlackCell(from)) {
            throw new GeneralError("general error");
        }
        if (!isBusyCell(from)) {
            throw new GeneralError("general error");
        }
        if (movingTower.getColor() != playerColor) {
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
        Tower movingTower = field.getTower(from);

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
        if (!movingTower.isKing() && !isNearby(from, to)) {
            throw new GeneralError("general error");
        }
        if (movingTower.isKing()) {
            int maxShift = Math.abs(to.getX() - from.getX());
            int xOffset = (to.getX() - from.getX()) / Math.abs(to.getX() - from.getX());
            int yOffset = (to.getY() - from.getY()) / Math.abs(to.getY() - from.getY());

            for (int shift = 1; shift < maxShift; ++shift) {
                Tower currentCellTower = field.getTower(from.getX() + shift * xOffset, from.getY() + shift * yOffset);
                if (currentCellTower != null) {
                    throw new GeneralError("general error");
                }
            }
        }

        field.moveTower(from, to);
    }

    private boolean isBlackCell(Coordinates coords) {
        return (coords.getX() + coords.getY()) % 2 == 0;
    }

    private boolean isBusyCell(Coordinates coords) {
        return field.getTower(coords) != null;
    }

    private boolean isOnOneDiagonal(Coordinates coords1, Coordinates coords2) {
        return ((coords2.getX() - coords1.getX()) == (coords2.getY() - coords1.getY())) ||
                ((coords2.getX() - coords1.getX()) == (coords1.getY() - coords2.getY()));
    }

    private boolean isNearby(Coordinates coords1, Coordinates coords2) {
        return ((Math.abs(coords1.getX() - coords2.getX()) == 1) && (Math.abs(coords1.getY()- coords2.getY()) == 1));
    }

    private void takingMove(Move move) throws GeneralError {
        Tower movingTower = field.getTower(move.nextCoord());

        while (move.hasNextCoord()) {
            Coordinates nextCoords = move.nextCoord();
            take(movingTower, nextCoords);
        }

        if (hasTakingMoveTower(movingTower)) {
            throw new InvalidMoveException("invalid move");
        }

        for (int i = 0; i < field.SIZE; ++i) {
            for (int j = 0; j < field.SIZE; ++j) {
                if (field.getTower(i, j) != null && field.getTower(i, j).isKilled()) {
                    field.deleteTower(new Coordinates(i, j));
                }
            }
        }
    }

    private void take(Tower movingTower, Coordinates to) throws GeneralError {
        Coordinates from = movingTower.getCoordinates();

        if (isBusyCell(to)) {
            throw new BusyCellException("busy cell");
        }
        if (!isBlackCell(to)) {
            throw new WhiteCellException("white cell");
        }
        if (!isOnOneDiagonal(from, to)) {
            throw new GeneralError("general error");
        }

        if (!movingTower.isKing()) {
            if (!isTakingNearby(from, to)) {
                throw new GeneralError("general error");
            }

            Coordinates underTakingCheckerCoords = new Coordinates((from.getX() + to.getX()) / 2, (from.getY() + to.getY()) / 2);
            if (field.getTower(underTakingCheckerCoords) == null) {
                throw new GeneralError("general error");
            }
            if (field.getTower(underTakingCheckerCoords).getColor() == movingTower.getColor() || field.getTower(underTakingCheckerCoords).isKilled()) {
                throw new GeneralError("general error");
            }

            field.getTower(underTakingCheckerCoords).kill();
            movingTower.addChecker(field.getTower(underTakingCheckerCoords).takeTopChecker());
            field.moveTower(from, to);
        } else {
            int maxShift = Math.abs(to.getX() - from.getX());
            int xOffset = (to.getX() - from.getX()) / Math.abs(to.getX() - from.getX());
            int yOffset = (to.getY() - from.getY()) / Math.abs(to.getY() - from.getY());

            Tower underTakingTower = null;
            int underTakingTowersCount = 0;

            for (int shift = 1; shift < maxShift; ++shift) {
                Tower currentCellTower = field.getTower(from.getX() + shift * xOffset, from.getY() + shift * yOffset);
                if (currentCellTower != null &&
                    (currentCellTower.getColor() == movingTower.getColor() || currentCellTower.isKilled())) {
                    throw new GeneralError("general error");
                }
                if (currentCellTower != null &&
                    currentCellTower.getColor() != movingTower.getColor()) {
                    ++underTakingTowersCount;
                    underTakingTower = currentCellTower;
                }
            }

            if (underTakingTowersCount != 1) {
                throw new GeneralError("general error");
            }

            underTakingTower.kill();
            movingTower.addChecker(underTakingTower.takeTopChecker());
            field.moveTower(from, to);
        }
    }

    private boolean isTakingNearby(Coordinates coords1, Coordinates coords2) {
        return ((Math.abs(coords1.getX() - coords2.getX()) == 2) && (Math.abs(coords1.getY()- coords2.getY()) == 2));
    }

    private boolean hasTakingMove(Color playerColor) {
        ArrayList<Tower> playerTowers;
        if (playerColor == Color.WHITE) {
            playerTowers = field.getPosition().getWhiteTowers();
        } else {
            playerTowers = field.getPosition().getBlackTowers();
        }

        boolean answer = false;

        for (Tower tower: playerTowers) {
            if (hasTakingMoveTower(tower)) {
                answer = true;
                break;
            }
        }

        return answer;
    }

    private boolean hasTakingMoveTower(Tower tower) {
        int[][] offset = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        Coordinates coords = tower.getCoordinates();
        boolean answer = false;

        if (!tower.isKing()) {
            for (int i = 0; i < offset.length; ++i) {
                int currentToX = coords.getX() + 2 * offset[i][0];
                int currentToY = coords.getY() + 2 * offset[i][1];

                if (currentToX < field.SIZE && currentToY < field.SIZE && currentToX >= 0 && currentToY >= 0) {
                    Tower currentToTower = field.getTower(currentToX, currentToY);
                    Tower currentCellTower = field.getTower(coords.getX() + offset[i][0], coords.getY() + offset[i][1]);
                    if (currentToTower == null && currentCellTower != null &&
                        currentCellTower.getColor() != tower.getColor() && !currentCellTower.isKilled()) {
                        answer = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < offset.length; ++i) {
                int xOffset = offset[i][0];
                int yOffset = offset[i][1];

                for (int shift = 1; shift < field.SIZE; ++shift) {
                    int currentToX = coords.getX() + shift * xOffset;
                    int currentToY = coords.getY() + shift * yOffset;

                    if (currentToX < field.SIZE && currentToY < field.SIZE && currentToX >= 0 && currentToY >= 0) {
                        Tower currentToTower = field.getTower(currentToX, currentToY);

                        if (currentToTower != null && currentToTower.getColor() == tower.getColor()) {
                            break;
                        }
                        if (currentToTower != null && currentToTower.getColor() != tower.getColor()) {
                            int nextToX = coords.getX() + (shift + 1) * xOffset;
                            int nextToY = coords.getY() + (shift + 1) * yOffset;

                            if (nextToX < field.SIZE && nextToY < field.SIZE && nextToX >= 0 && nextToY >= 0 &&
                                field.getTower(nextToX, nextToY) == null && !currentToTower.isKilled()) {
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
