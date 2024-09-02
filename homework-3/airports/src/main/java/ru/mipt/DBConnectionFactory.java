package ru.mipt;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionFactory {
    private final static String pathToDb = "./sqlite/airportsDB.sqlite";
    private static final HikariConfig config = new HikariConfig();
    private static final DataSource ds;

    static {
        config.setJdbcUrl("jdbc:sqlite:" + pathToDb);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
        try {
            prepareDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void prepareDB() throws SQLException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists aircrafts");
            statement.executeUpdate("drop table if exists airports");
            statement.executeUpdate("drop table if exists boarding_passes");
            statement.executeUpdate("drop table if exists bookings");
            statement.executeUpdate("drop table if exists flights");
            statement.executeUpdate("drop table if exists seats");
            statement.executeUpdate("drop table if exists ticket_flights");
            statement.executeUpdate("drop table if exists tickets");

            statement.executeUpdate("create table aircrafts (" +
                                            "aircraft_code char(3) not null," +
                                            "model jsonb not null," +
                                            "range integer not null" +
                                        ")"
            );

            statement.executeUpdate("create table airports (" +
                                            "airport_code char(3) not null," +
                                            "airport_name jsonb not null," +
                                            "city jsonb not null," +
                                            "coordinates point not null," +
                                            "timezone text not null" +
                                        ")"
            );

            statement.executeUpdate("create table boarding_passes (" +
                                            "ticket_no char(13) not null," +
                                            "flight_id integer not null," +
                                            "boarding_no integer not null," +
                                            "seat_no varchar(4) not null" +
                                        ")"
            );

            statement.executeUpdate("create table bookings (" +
                                            "book_ref char(6) not null," +
                                            "book_date timestamptz not null," +
                                            "total_amount numeric(10,2) not null" +
                                        ")"
            );

            statement.executeUpdate("create table flights (" +
                                            "flight_id serial not null," +
                                            "flight_no char(6) not null," +
                                            "scheduled_departure timestamptz not null," +
                                            "scheduled_arrival timestamptz not null," +
                                            "departure_airport char(3) not null," +
                                            "arrival_airport char(3) not null," +
                                            "status varchar(20) not null," +
                                            "aircraft_code char(3) not null," +
                                            "actual_departure timestamptz," +
                                            "actual_arrival timestamptz" +
                                        ")"
            );

            statement.executeUpdate("create table seats (" +
                                            "aircraft_code char(3) not null," +
                                            "seat_no varchar(4) not null," +
                                            "fare_conditions varchar(10) not null" +
                                        ")"
            );

            statement.executeUpdate("create table ticket_flights (" +
                                            "ticket_no char(13) not null," +
                                            "flight_id integer not null," +
                                            "fare_conditions varchar(10) not null," +
                                            "amount numeric(10,2) not null" +
                                        ")"
            );

            statement.executeUpdate("create table tickets (" +
                                            "ticket_no char(13) not null," +
                                            "book_ref char(6) not null," +
                                            "passenger_id varchar(20) not null," +
                                            "passenger_name text not null," +
                                            "contact_data jsonb" +
                                        ")"
            );
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
