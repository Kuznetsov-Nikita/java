package ru.mipt;

import java.io.IOException;

/**
 * Класс игры "Шашки"
 */
public class CheckersGame {
    /** Поле игровое состояние */
    private GameState gameState;

    /**
     * Инициализация игры
     */
    public void initGame() {
        Position startPosition = InputOutputHandler.readPosition();
        gameState = new GameState(startPosition);
    }

    /**
     * Процесс игры
     * @throws GeneralError Любая ошибка, произошедшая в процессе игры
     */
    public void playGame() throws GeneralError {
        boolean hasNextMove = true;

        while (hasNextMove) {
            try {
                Move move = InputOutputHandler.readMove();
                gameState.makeMove(move);
            } catch (IOException exception) {
                hasNextMove = false;
            }
        }
    }

    /**
     * Завершение игры
     */
    public void finishGame() {
        InputOutputHandler.printPosition(gameState.getPosition());
    }
}