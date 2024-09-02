package ru.mipt;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, отвечающий за преобразования информации о зодах и позициях из текстового вида в данные
 */
public class Parser {
    /**
     * Получить из строки объект игровую позицию
     * @param stringWhiteCheckersPositions Строка с координатами белых шашек
     * @param stringBlackCheckersPositions Строка с координатами черных шашек
     * @return Позиция, задаваемая строками с координатами шашек
     */
    static public Position stringToPosition(String stringWhiteCheckersPositions, String stringBlackCheckersPositions) {
        return new Position(getCheckers(stringWhiteCheckersPositions, Color.WHITE),
                            getCheckers(stringBlackCheckersPositions, Color.BLACK));
    }

    static private ArrayList<Checker> getCheckers(String stringCheckersPositions, Color checkersColor) {
        Pattern pattern = Pattern.compile("([a-hA-H][1-8]\\s*)");
        Matcher matcher = pattern.matcher(stringCheckersPositions);

        ArrayList<Checker> checkers = new ArrayList<>();

        while (matcher.find()) {
            String stringCoords = matcher.group(1);
            Coordinates coords;
            boolean isKing;

            if (stringCoords.charAt(0) >= 'A' && stringCoords.charAt(0) <= 'H') {
                coords = new Coordinates((int)stringCoords.charAt(0) - 'A',
                                                    (int)stringCoords.charAt(1) - '1');
                isKing = true;
            } else {
                coords = new Coordinates((int)stringCoords.charAt(0) - 'a',
                        (int)stringCoords.charAt(1) - '1');
                isKing = false;
            }

            checkers.add(new Checker(coords, checkersColor, isKing));
        }

        return checkers;
    }

    /**
     * Получить из строки, задающей ход, объект ход
     * @param stringMove Строковое представление хода
     * @param moveNumber Текущий номер хода
     * @return Ход, задаваемый данной строкой
     */
    static public Move stringToMove(String stringMove, int moveNumber) {
        Color playerColor = (moveNumber % 2 == 0) ? Color.WHITE : Color.BLACK;
        boolean isTaking = (stringMove.charAt(2) == ':');

        Pattern pattern = Pattern.compile("([a-hA-H][1-8][:-]?)");
        Matcher matcher = pattern.matcher(stringMove);

        ArrayList<Coordinates> moveCoords = new ArrayList<>();

        while (matcher.find()) {
            String stringCoords = matcher.group(1);
            stringCoords = stringCoords.toLowerCase();
            Coordinates coords = new Coordinates((int)stringCoords.charAt(0) - 'a',
                                                (int)stringCoords.charAt(1) - '1');
            moveCoords.add(coords);
        }

        return new Move(playerColor, isTaking, moveCoords);
    }
}
