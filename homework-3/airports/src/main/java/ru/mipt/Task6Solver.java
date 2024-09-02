package ru.mipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Task6Solver {
    private static final String UPDATE_QUERY = "update flights " +
                                               "set status = 'Cancelled' " +
                                               "where aircraft_code in " +
                                                    "(select aircraft_code " +
                                                     "from aircrafts " +
                                                     "where json_extract(model, '$.ru') == ?) and (status == 'Scheduled' or status == 'On Time' or status == 'Delayed')";
    private static final String DELETE_QUERY_1 = "delete from tickets " +
                                                 "where ticket_no in " +
                                                    "(select ticket_no " +
                                                    "from (ticket_flights inner join flights on ticket_flights.flight_id == flights.flight_id) inner join aircrafts on flights.aircraft_code == aircrafts.aircraft_code " +
                                                    "where json_extract(model, '$.ru') == ? and status == 'Cancelled')";
    private static final String DELETE_QUERY_2 = "delete from ticket_flights " +
                                                 "where flight_id in " +
                                                    "(select flight_id " +
                                                    "from flights inner join aircrafts on flights.aircraft_code == aircrafts.aircraft_code " +
                                                    "where json_extract(model, '$.ru') == ? and status == 'Cancelled')";

    public static void solve(String aircraftModel) {
        try (Connection connection = DBConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
                preparedStatement.setString(1, aircraftModel);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                connection.rollback();
                System.exit(-1);
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY_1)) {
                preparedStatement.setString(1, aircraftModel);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                connection.rollback();
                System.exit(-1);
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY_2)) {
                preparedStatement.setString(1, aircraftModel);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                connection.rollback();
                System.exit(-1);
            }

            connection.commit();
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
