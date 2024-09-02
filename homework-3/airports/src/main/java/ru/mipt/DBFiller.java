package ru.mipt;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBFiller {
    public static void fillAll() {
        fillAircrafts();
        fillAirports();
        fillBoardingPasses();
        fillBookings();
        fillFlights();
        fillSeats();
        fillTicketFlights();
        fillTickets();
    }

    public static void fillAircrafts() {
        final String INSERT_AIRCRAFTS_QUERY = "insert into aircrafts (aircraft_code, model, range) VALUES (?, json(?), ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/aircrafts_data.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_AIRCRAFTS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setString(2, record[1]);
                    stmt.setInt(3, Integer.valueOf(record[2]));
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillAirports() {
        final String INSERT_AIRPORTS_QUERY = "insert into airports (airport_code, airport_name, city, coordinates, timezone) VALUES (?, json(?), json(?), ?, ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/airports_data.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_AIRPORTS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setString(2, record[1]);
                    stmt.setString(3, record[2]);
                    stmt.setString(4, record[3]);
                    stmt.setString(5, record[4]);
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillBoardingPasses() {
        final String INSERT_BOARDING_PASSES_QUERY = "insert into boarding_passes (ticket_no, flight_id, boarding_no, seat_no) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/boarding_passes.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_BOARDING_PASSES_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setInt(2, Integer.valueOf(record[1]));
                    stmt.setInt(3, Integer.valueOf(record[2]));
                    stmt.setString(4, record[3]);
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillBookings() {
        final String INSERT_BOOKINGS_QUERY = "insert into bookings (book_ref, book_date, total_amount) VALUES (?, ?, ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/bookings.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_BOOKINGS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setString(2, record[1].replace("+03", ""));
                    stmt.setFloat(3, Float.valueOf(record[2]));
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillFlights() {
        final String INSERT_FLIGHTS_QUERY = "insert into flights (flight_id, flight_no, scheduled_departure, scheduled_arrival, departure_airport, arrival_airport, status, aircraft_code, actual_departure, actual_arrival) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/flights.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_FLIGHTS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setInt(1, Integer.valueOf(record[0]));
                    stmt.setString(2, record[1]);
                    stmt.setString(3, record[2].replace("+03", ""));
                    stmt.setString(4, record[3].replace("+03", ""));
                    stmt.setString(5, record[4]);
                    stmt.setString(6, record[5]);
                    stmt.setString(7, record[6]);
                    stmt.setString(8, record[7]);
                    stmt.setString(9, record[8].replace("+03", ""));
                    stmt.setString(10, record[9].replace("+03", ""));
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillSeats() {
        final String INSERT_SEATS_QUERY = "insert into seats (aircraft_code, seat_no, fare_conditions) VALUES (?, ?, ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/seats.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_SEATS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setString(2, record[1]);
                    stmt.setString(3, record[2]);
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillTicketFlights() {
        final String INSERT_TICKET_FLIGHTS_QUERY = "insert into ticket_flights (ticket_no, flight_id, fare_conditions, amount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/ticket_flights.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_TICKET_FLIGHTS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setInt(2, Integer.valueOf(record[1]));
                    stmt.setString(3, record[2]);
                    stmt.setFloat(4, Float.valueOf(record[3]));
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    public static void fillTickets() {
        final String INSERT_TICKETS_QUERY = "insert into tickets (ticket_no, book_ref, passenger_id, passenger_name, contact_data) VALUES (?, ?, ?, ?, json(?))";

        try (Connection conn = DBConnectionFactory.getConnection()) {
            try (CSVReader reader = new CSVReader(new FileReader("./airtrans/tickets.csv"));
                 PreparedStatement stmt = conn.prepareStatement(INSERT_TICKETS_QUERY)) {
                conn.setAutoCommit(false);

                String[] record;
                while ((record = reader.readNext()) != null) {
                    stmt.setString(1, record[0]);
                    stmt.setString(2, record[1]);
                    stmt.setString(3, record[2]);
                    stmt.setString(4, record[3]);
                    stmt.setString(5, record[4]);
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch(Exception e) {
                conn.rollback();
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.exit(-1);
        }
    }
}
