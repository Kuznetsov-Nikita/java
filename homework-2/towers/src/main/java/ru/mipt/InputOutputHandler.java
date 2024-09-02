package ru.mipt;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс, отвечающий за ввод и вывод данных об игре
 */
public class InputOutputHandler {
    /** Сканнер */
    final static private Scanner scanner = new Scanner(System.in);
    /** Номер текущего хода */
    static private int moveNumber = 0;

    /**
     * Прочитать начальную позицию игры
     * @return Позиция игры
     */
    static public Position readPosition() {
        String whiteTowersCoords = scanner.nextLine();
        String blackTowersCoords = scanner.nextLine();

        return Parser.stringToPosition(whiteTowersCoords, blackTowersCoords);
    }

    /**
     * Прочитать очередной ход
     * @return Ход
     * @throws IOException Ошибка чтения - нечего читать
     */
    static public Move readMove() throws IOException {
        if (scanner.hasNext()) {
            return Parser.stringToMove(scanner.next(), moveNumber++);
        } else {
            throw new IOException();
        }
    }

    /**
     * Распечатать данную позицию игры
     * @param position Позиция для вывода
     */
    static public void printPosition(Position position) {
        System.out.println(position.toString());
    }

    /**
     * Вывести сообщение об ошибке
     * @param message Сообщение
     */
    static public void printError(String message) {
        System.out.println(message);
    }
}
