package ru.mipt;

import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        DBFiller.fillAll();

        System.out.println("Задание 1");
        Task1Solver.solve();

        System.out.println("\nЗадание 2");
        Task2Solver.solve();

        System.out.println("\nЗадание 3");
        Task3Solver.solve();

        System.out.println("\nЗадание 4");
        Task4Solver.solve();

        System.out.println("\nЗадание 5");
        Task5Solver.solve();

        System.out.println("\nЗадание 6");
        System.out.println("Введите модель самолета (на русском):");
        try (Scanner sc = new Scanner(System.in)) {
            String aircraftModel = sc.nextLine();
            Task6Solver.solve(aircraftModel);
        }
    }
}
