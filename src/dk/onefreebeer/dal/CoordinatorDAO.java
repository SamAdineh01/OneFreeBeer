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

    private final ConnectionManager connectionManager = new ConnectionManager();
    public CoordinatorDAO(){
        generateEvents();
    }


    private void generateEvents(){
//        eventList.add(new Event(1, "Free Beer", "Please bring some warm clothes.", "City Center", "20:00", "23:00", "11.04.2024"));
//        eventList.add(new Event(2, "Absolutes", "No Information.", "EASV Bar", "21:00", "23:00", "23.03.2024"));

    }

    public List<Event> getEventList(){
        return getAllEvents();
    }


    public boolean createEvent(Event event) {
        String sql = "INSERT INTO Events VALUES ( ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getNote());
            pstmt.setString(3, event.getLocation());
            pstmt.setString(5, event.getDate());
            pstmt.setString(6, event.getTime());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("note"),
                        rs.getString("location"),
                        rs.getString("start"),
                        rs.getString("end"),
                        rs.getString("date")));
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
                    // Return the generated ticket ID
                    ticket.setId(generatedKeys.getInt(1));
                } else {
                    // No generated keys found
                    throw new SQLException("Creating ticket failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.id AS ticket_id, t.ticket_type, t.uuid, e.id AS event_id, e.title, e.note, e.location, e.start, e.[end], e.date FROM Tickets t JOIN Events e ON t.event_id = e.id";
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
        String sql = "SELECT t.id AS ticket_id, t.ticket_type, t.uuid, e.id AS event_id, e.title, e.note, e.location, e.start, e.[end], e.date " +
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


