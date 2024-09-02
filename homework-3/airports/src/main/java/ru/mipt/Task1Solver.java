package ru.mipt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task1Solver {
    private static final String QUERY = "select json_extract(city, '$.ru') as city, " +
                                               "group_concat(airport_code, ', ') as airports_list " +
                                        "from airports " +
                                        "group by city " +
                                        "having count(airport_name) >= 2";

    public static void solve() {
        try (Connection connection = DBConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(QUERY)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("city") + " " + resultSet.getString("airports_list"));
                }
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
