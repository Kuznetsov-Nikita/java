package ru.mipt;

public final class App {
    public static void main(String[] args) {
        TowersGame game = new TowersGame();

        game.initGame();

        try {
            game.playGame();
            game.finishGame();
        } catch (GeneralError e) {
            InputOutputHandler.printError(e.getMessage());
        }
    }
}
