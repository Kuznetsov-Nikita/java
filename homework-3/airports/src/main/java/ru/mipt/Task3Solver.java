package ru.mipt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task3Solver {
    private static final String QUERY = "select city, arrival_airport, avg " +
                                        "from (select json_extract(city, '$.ru') as city, arrival_airport, avg " +
                                        "from airports inner join " +
                                            "(select departure_airport, arrival_airport, avg((julianday(actual_arrival) - julianday(actual_departure)) * 24 * 60 * 60) as avg " +
                                             "from flights " +
                                             "where status == 'Arrived' " +
                                             "group by departure_airport, arrival_airport " +
                                             "order by avg) as tmp " +
                                        "on airports.airport_code == tmp.departure_airport " +
                                        "order by avg) " +
                                        "group by city";

    public static void solve() {
        try (Connection connection = DBConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(QUERY)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("city") + " " +
                                       resultSet.getString("arrival_airport") + " " +
                                       resultSet.getString("avg"));
                }
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
