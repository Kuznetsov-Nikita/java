package ru.mipt;

public final class App {
    public static void main(String[] args) {
        CheckersGame game = new CheckersGame();

        game.initGame();

        try {
            game.playGame();
            game.finishGame();
        } catch (GeneralError e) {
            InputOutputHandler.printError(e.getMessage());
        }
    }
}
