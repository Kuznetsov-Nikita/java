package ru.mipt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task2Solver {
    private static final String QUERY = "select json_extract(city, '$.ru') as city, count(flight_id) as count " +
                                        "from flights inner join airports on flights.departure_airport == airports.airport_code " +
                                        "where status == 'Cancelled' " +
                                        "group by city " +
                                        "order by count desc " +
                                        "limit 5";

    public static void solve() {
        try (Connection connection = DBConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(QUERY)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString( "city") + " " + resultSet.getString("count"));
                }
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
