package ru.mipt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class GameStateTest {
    GameState gameState;

    @Test
    void savePositionTest() {
        String whiteCheckersString = "a1 a3 b2 c1 c3 d2 e1 e3 f2 g1 g3 h2";
        String blackCheckersString = "a7 b6 b8 c7 d6 d8 e7 f6 f8 g7 h6 h8";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);
        Position currentPosition = gameState.getPosition();

        Assertions.assertEquals(currentPosition.toString(), initPosition.toString());
    }

    @Test
    void okQuiteMove() {
        String whiteCheckersString = "a1";
        String blackCheckersString = "a7";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringMove = "a1-b2";
        Move quiteMove = Parser.stringToMove(stringMove, 0);
        Assertions.assertDoesNotThrow(() -> gameState.makeMove(quiteMove));

        Position currentPosition = gameState.getPosition();

        whiteCheckersString = "b2";
        Position finalPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        Assertions.assertEquals(currentPosition.toString(), finalPosition.toString());
    }

    @Test
    void okTakingMove() {
        String whiteCheckersString = "b2";
        String blackCheckersString = "c3";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringMove = "b2:d4";
        Move takingMove = Parser.stringToMove(stringMove, 0);

        Assertions.assertDoesNotThrow(() -> gameState.makeMove(takingMove));


        Position currentPosition = gameState.getPosition();

        whiteCheckersString = "d4";
        blackCheckersString = "";
        Position finalPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        Assertions.assertEquals(currentPosition.toString(), finalPosition.toString());
    }

    @Test
    void quiteMoveExceptions() {
        String whiteCheckersString = "a1 b2";
        String blackCheckersString = "d8";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringBusyCellMove = "a1-b2";
        Move busyCellMove = Parser.stringToMove(stringBusyCellMove, 0);
        Assertions.assertThrows(BusyCellException.class, () -> gameState.makeMove(busyCellMove));

        String stringWhiteCellMove = "a1-a2";
        Move whiteCellMove = Parser.stringToMove(stringWhiteCellMove, 0);
        Assertions.assertThrows(WhiteCellException.class, () -> gameState.makeMove(whiteCellMove));

        String stringGeneralErrorMove = "b2-g3";
        Move generalErrorMove = Parser.stringToMove(stringGeneralErrorMove, 0);
        Assertions.assertThrows(GeneralError.class, () -> gameState.makeMove(generalErrorMove));
    }

    @Test
    void invalidMoveException() {
        String whiteCheckersString = "B2";
        String blackCheckersString = "d4";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringInvalidMove = "b2-a3";
        Move invalidMove = Parser.stringToMove(stringInvalidMove, 0);
        Assertions.assertThrows(InvalidMoveException.class, () -> gameState.makeMove(invalidMove));

    }
}
