import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static ArrayList<Integer> findMaximumSum(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> answer = new ArrayList<>(Arrays.asList(0, 0));

        int maxSum = a.get(0) + b.get(0);
        int maxElemOfAIndex = 0;

        for (int i = 1; i < a.size(); i++) {
            if (a.get(i) > a.get(maxElemOfAIndex)) {
                maxElemOfAIndex = i;
            }
            if (a.get(maxElemOfAIndex) + b.get(i) > maxSum) {
                maxSum = a.get(maxElemOfAIndex) + b.get(i);
                answer.set(0, maxElemOfAIndex);
                answer.set(1, i);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            for (int i = 0; i < n; ++i) {
                a.add(scanner.nextInt());
            }
            for (int i = 0; i < n; ++i) {
                b.add(scanner.nextInt());
            }
        }
        
        ArrayList<Integer> answer = findMaximumSum(a, b);
        System.out.println(answer.get(0).toString() + " " + answer.get(1).toString());
    }
}
