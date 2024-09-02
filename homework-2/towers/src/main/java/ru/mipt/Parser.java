package ru.mipt;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, отвечающий за преобразования информации о зодах и позициях из текстового вида в данные
 */
public class Parser {
    /**
     * Получить из строки объект игровую позицию
     * @param stringWhiteTowersPositions Строка с координатами белых башен
     * @param stringBlackTowersPositions Строка с координатами черных башен
     * @return Позиция, задаваемая строками с координатами шашек
     */
    static public Position stringToPosition(String stringWhiteTowersPositions, String stringBlackTowersPositions) {
        return new Position(getTowers(stringWhiteTowersPositions),
                            getTowers(stringBlackTowersPositions));
    }

    static private ArrayList<Tower> getTowers(String stringTowersPositions) {
        Pattern pattern = Pattern.compile("([a-hA-H][1-8]_[bwBW]+\\s*)");
        Matcher matcher = pattern.matcher(stringTowersPositions);

        ArrayList<Tower> towers = new ArrayList<>();

        while (matcher.find()) {
            String stringCoords = matcher.group(1);
            Coordinates coords;

            if (stringCoords.charAt(0) >= 'A' && stringCoords.charAt(0) <= 'H') {
                coords = new Coordinates((int)stringCoords.charAt(0) - 'A',
                                                    (int)stringCoords.charAt(1) - '1');
            } else {
                coords = new Coordinates((int)stringCoords.charAt(0) - 'a',
                        (int)stringCoords.charAt(1) - '1');
            }

            ArrayDeque<Checker> checkers = new ArrayDeque<>();

            for (int i = 3; i < stringCoords.length(); ++i) {
                switch (stringCoords.charAt(i)) {
                    case 'w':
                        checkers.addLast(new Checker(coords, Color.WHITE, false));
                        break;
                    case 'W':
                        checkers.addLast(new Checker(coords, Color.WHITE, true));
                        break;
                    case 'b':
                        checkers.addLast(new Checker(coords, Color.BLACK, false));
                        break;
                    case 'B':
                        checkers.addLast(new Checker(coords, Color.BLACK, true));
                        break;
                }
            }

            towers.add(new Tower(coords, checkers));
        }

        return towers;
    }

    /**
     * Получить из строки, задающей ход, объект ход
     * @param stringMove Строковое представление хода
     * @param moveNumber Текущий номер хода
     * @return Ход, задаваемый данной строкой
     */
    static public Move stringToMove(String stringMove, int moveNumber) {
        Color playerColor = (moveNumber % 2 == 0) ? Color.WHITE : Color.BLACK;
        boolean isTaking = stringMove.contains(":");

        Pattern pattern = Pattern.compile("([a-hA-H][1-8]_[bwBW]+[:-]?)");
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
