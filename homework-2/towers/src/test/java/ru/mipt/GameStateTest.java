package ru.mipt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class GameStateTest {
    GameState gameState;

    @Test
    void savePositionTest() {
        String whiteTowersString = "a7_wbb b2_ww c1_w e1_w f2_w g1_w";
        String blackTowersString = "b4_bwww b8_b c3_b c7_b e5_bww e7_b f8_b g5_b g7_b h8_b";
        Position initPosition = Parser.stringToPosition(whiteTowersString, blackTowersString);

        gameState = new GameState(initPosition);
        Position currentPosition = gameState.getPosition();

        Assertions.assertEquals(currentPosition.toString(), initPosition.toString());
    }

    @Test
    void okQuiteMove() {
        String whiteCheckersString = "a1_wbb";
        String blackCheckersString = "a7_b";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringMove = "a1_wbb-b2_wbb";
        Move quiteMove = Parser.stringToMove(stringMove, 0);
        Assertions.assertDoesNotThrow(() -> gameState.makeMove(quiteMove));

        Position currentPosition = gameState.getPosition();

        whiteCheckersString = "b2_wbb";
        Position finalPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        Assertions.assertEquals(currentPosition.toString(), finalPosition.toString());
    }

    @Test
    void okTakingMove() {
        String whiteCheckersString = "b2_wb";
        String blackCheckersString = "c3_bw";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringMove = "b2_wb:d4_wbb";
        Move takingMove = Parser.stringToMove(stringMove, 0);

        Assertions.assertDoesNotThrow(() -> gameState.makeMove(takingMove));


        Position currentPosition = gameState.getPosition();

        whiteCheckersString = "c3_w d4_wbb";
        blackCheckersString = "";
        Position finalPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        Assertions.assertEquals(currentPosition.toString(), finalPosition.toString());
    }

    @Test
    void quiteMoveExceptions() {
        String whiteCheckersString = "a1_wbb b2_w";
        String blackCheckersString = "d8_bb";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringBusyCellMove = "a1_wbb-b2_wbb";
        Move busyCellMove = Parser.stringToMove(stringBusyCellMove, 0);
        Assertions.assertThrows(BusyCellException.class, () -> gameState.makeMove(busyCellMove));

        String stringWhiteCellMove = "a1_wbb-a2_wbb";
        Move whiteCellMove = Parser.stringToMove(stringWhiteCellMove, 0);
        Assertions.assertThrows(WhiteCellException.class, () -> gameState.makeMove(whiteCellMove));

        String stringGeneralErrorMove = "b2_w-g3_w";
        Move generalErrorMove = Parser.stringToMove(stringGeneralErrorMove, 0);
        Assertions.assertThrows(GeneralError.class, () -> gameState.makeMove(generalErrorMove));
    }

    @Test
    void invalidMoveException() {
        String whiteCheckersString = "B2_W";
        String blackCheckersString = "d4_b";
        Position initPosition = Parser.stringToPosition(whiteCheckersString, blackCheckersString);

        gameState = new GameState(initPosition);

        String stringInvalidMove = "b2_W-a3_W";
        Move invalidMove = Parser.stringToMove(stringInvalidMove, 0);
        Assertions.assertThrows(InvalidMoveException.class, () -> gameState.makeMove(invalidMove));

    }
}
