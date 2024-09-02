import java.util.Scanner;

public class SecondTask {
    private final static short MAXSIZE = 1000;

    private static double findArea(short n, short[][] vertices) {
        double area = 0.0;

        for (short i = 0; i < n; ++i) {
            short firstX, firstY;
            if (i == 0) {
                firstX = vertices[n - 1][0];
                firstY = vertices[n - 1][1];
            } else {
                firstX = vertices[i - 1][0];
                firstY = vertices[i - 1][1];
            }

            short secondX = vertices[i][0];
            short secondY = vertices[i][1];

            area += (secondX - firstX) * (firstY + secondY);
        }

        return Math.abs(area / 2);
    }

    public static void main(String[] args) {
        short[][] vertices = new short[MAXSIZE][2];
        short n;

        try (Scanner scanner = new Scanner(System.in)) {
            n = scanner.nextShort();

            for (short i = 0; i < n; ++i) {
                vertices[i][0] = scanner.nextShort();
                vertices[i][1] = scanner.nextShort();
            }
        }

        System.out.println(findArea(n, vertices));
    }
}
