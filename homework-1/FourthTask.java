import java.util.LinkedList;
import java.util.Scanner;

public class FourthTask {
    private static int findSurvivor(int n, int k) {
        LinkedList<Integer> currentSurvivors = new LinkedList<>();

        for (int i = 1; i < n + 1; ++i) {
            currentSurvivors.add(i);
        }

        int i = 0;

        while (currentSurvivors.size() > 1) {
            i = (i + k - 1) % currentSurvivors.size();
            currentSurvivors.remove(i);
        }

        return currentSurvivors.get(0);
    }

    public static void main(String[] args) {
        int n;
        int k;

        try (Scanner scanner = new Scanner(System.in)) {
            n = scanner.nextInt();
            k = scanner.nextInt();
        }

        System.out.println(findSurvivor(n, k));
    }
}
