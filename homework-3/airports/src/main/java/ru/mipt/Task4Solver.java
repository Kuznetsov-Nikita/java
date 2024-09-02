package ru.mipt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task4Solver {
    private static final String QUERY = "select month, count(status) as cnt " +
                                        "from (select strftime('%m', scheduled_departure) as month, status " +
                                              "from flights " +
                                              "where status == 'Cancelled') as tmp " +
                                        "group by month";

    public static void solve() {
        try (Connection connection = DBConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(QUERY)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("month") + " " + resultSet.getString("cnt"));
                }
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
