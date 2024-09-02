package ru.mipt;

/**
 * Класс двумерных координат
 */
public class Coordinates {
    /** Первая координата */
    private int x;
    /** Вторая координата */
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Преобразовать координаты в строковый вид
     * @return Строковое представление координат
     */
    public String toString() {
        char[] coords = new char[2];
        coords[0] = (char)(x + 'a');
        coords[1] = (char)(y + '1');
        return new String(coords);
    }
}
