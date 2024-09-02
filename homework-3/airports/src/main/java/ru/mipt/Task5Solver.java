package ru.mipt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task5Solver {
    private static final String QUERY_1 = "select day_of_week, count(flight_id) as cnt from " +
                                          "(select strftime('%w', scheduled_departure) as day_of_week, flight_id, json_extract(city, '$.en') " +
                                           "from flights inner join airports on flights.departure_airport == airports.airport_code " +
                                           "where json_extract(airports.city, '$.en') == 'Moscow') as tmp1 " +
                                          "group by day_of_week";
    private static final String QUERY_2 = "select day_of_week, count(flight_id) as cnt from " +
                                          "(select strftime('%w', scheduled_arrival) as day_of_week, flight_id, json_extract(city, '$.en') " +
                                           "from flights inner join airports on flights.arrival_airport == airports.airport_code " +
                                           "where json_extract(airports.city, '$.en') == 'Moscow') as tmp1 " +
                                          "group by day_of_week";

    public static void solve() {
        try (Connection connection = DBConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            System.out.println("* Дни недели: 0 - вс, 1 - пн, 2 - вт, 3 - ср, 4 - чт, 5 - пт, 6 - сб");
            System.out.println("Рейсы из Москвы:");
            try (ResultSet resultSet = stmt.executeQuery(QUERY_1)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("day_of_week") + " " + resultSet.getString("cnt"));
                }
            }
            System.out.println("Рейсы в Москву:");
            try (ResultSet resultSet = stmt.executeQuery(QUERY_2)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("day_of_week") + " " + resultSet.getString("cnt"));
                }
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
