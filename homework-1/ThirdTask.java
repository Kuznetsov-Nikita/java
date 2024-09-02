import java.util.ArrayList;
import java.util.Scanner;

public class ThirdTask {
    private static int findRequiredSumCount(int k, ArrayList<Integer> a, ArrayList<Integer> b) {
        int requiredSumCount = 0;

        int n = a.size();
        int m = b.size();

        int i = 0;
        int j = m - 1;

        while (i < n && j >= 0) {
            int currentSum = a.get(i) + b.get(j);

            if (currentSum == k) {
                ++requiredSumCount;
                --j;
            } else if (currentSum > k) {
                --j;
            } else {
                ++i;
            }
        }

        return requiredSumCount;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        int k;

        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            for (int i = 0; i < n; ++i) {
                a.add(scanner.nextInt());
            }

            n = scanner.nextInt();
            for (int i = 0; i < n; ++i) {
                b.add(scanner.nextInt());
            }

            k = scanner.nextInt();
        }

        System.out.println(findRequiredSumCount(k, a, b));
    }
}
