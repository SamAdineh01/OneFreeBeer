package dk.onefreebeer.dal;

import dk.onefreebeer.be.Coordinator;
import dk.onefreebeer.be.Event;
import dk.onefreebeer.be.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoordinatorDAO {
    private List<Event> eventList = new ArrayList<>();

    private final Connection conn;
    private final ConnectionManager connectionManager = new ConnectionManager();

    {
        try {
            conn = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CoordinatorDAO() {
        generateEvents();
    }


    private void generateEvents() {
//        eventList.add(new Event(1, "Free Beer", "Please bring some warm clothes.", "City Center", "20:00", "23:00", "11.04.2024"));
//        eventList.add(new Event(2, "Absolutes", "No Information.", "EASV Bar", "21:00", "23:00", "23.03.2024"));

    }

    public List<Event> getEventList() {
        return getAllEvents();
    }


    public boolean createEvent(Event event) {
        String sql = "INSERT INTO Events (title, note, location, date, time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getNote());
            pstmt.setString(3, event.getLocation());
            pstmt.setString(4, event.getDate());
            pstmt.setString(5, event.getTime());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean editEvent(Event event) {
        String sql = "UPDATE Events SET title = ?, note = ?, location = ?, date = ?, time = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getNote());
            pstmt.setString(3, event.getLocation());
            pstmt.setString(4, event.getDate());
            pstmt.setString(5, event.getTime());
            pstmt.setInt(6, event.getId()); // Assuming you have an ID field for events

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEvent(int eventId) {
        String deleteTicketsQuery = "DELETE FROM Tickets WHERE event_id = ?";
        String deleteEvent = "DELETE FROM Events WHERE id = ?";
        try {
            conn.setAutoCommit(false);

            // Delete tickets first
            PreparedStatement pstmt = conn.prepareStatement(deleteTicketsQuery);
            pstmt.setInt(1, eventId);
            int rowsAffectedTickets = pstmt.executeUpdate();
            pstmt.close();

            // Delete event itself
            PreparedStatement pstmt2 = conn.prepareStatement(deleteEvent);
            pstmt2.setInt(1, eventId);
            int rowsAffectedEvent = pstmt2.executeUpdate();
            pstmt2.close();

            conn.commit();
            return (rowsAffectedTickets > 0 || rowsAffectedEvent > 0);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT id, title, note, location, date, time FROM Events"; // Remove the 'start' column from the SQL query
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("note"),
                        rs.getString("location"),
                        rs.getString("date"),
                        rs.getString("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    public boolean createTicket(Ticket ticket) {
        String sql = "INSERT INTO Tickets(event_id, ticket_type, uuid) VALUES ( ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, ticket.getEvent().getId());
            pstmt.setString(2, ticket.getType());
            pstmt.setString(3, ticket.getUuid().toString());
            int rows = pstmt.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Nothing executes yet ");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating ticket failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteTicket(Ticket ticket) {
        String sql = "DELETE FROM Tickets WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticket.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.id AS ticket_id, t.ticket_type, t.uuid, e.id AS event_id, e.title, e.note, e.location, e.date, e.time FROM Tickets t JOIN Events e ON t.event_id = e.id";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getString("title"),
                        rs.getString("note"),
                        rs.getString("location"),
                        rs.getString("date"),
                        rs.getString("time"));
                Ticket ticket = new Ticket(
                        rs.getInt("ticket_id"),
                        event,
                        rs.getString("ticket_type"),
                        UUID.fromString(rs.getString("uuid")));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }


    public Event getEventById(int id) {
        Event event = null;
        String sql = "SELECT * FROM Events WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Create the event if found
                    event = new Event(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("note"),
                            rs.getString("location"),
                            rs.getString("date"),
                            rs.getString("time"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return event;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Ticket getTicketById(int id) {
        Ticket ticket = null;
        String sql = "SELECT t.id AS ticket_id, t.ticket_type, t.uuid, e.id AS event_id, e.title, e.note, e.location, e.date, e.time " +
                "FROM Tickets t " +
                "JOIN Events e ON t.event_id = e.id " +
                "WHERE t.id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event(
                            rs.getInt("event_id"),
                            rs.getString("title"),
                            rs.getString("note"),
                            rs.getString("location"),
                            rs.getString("date"),
                            rs.getString("time"));
                    ticket = new Ticket(
                            rs.getInt("ticket_id"),
                            event,
                            rs.getString("ticket_type"),
                            UUID.fromString(rs.getString("uuid")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

}