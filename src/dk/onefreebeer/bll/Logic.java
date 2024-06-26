package dk.onefreebeer.bll;

import dk.onefreebeer.be.Event;
import dk.onefreebeer.be.Ticket;
import dk.onefreebeer.dal.CoordinatorDAO;

import java.util.List;

public class Logic {

    private CoordinatorDAO coordinatorDAO;

    public Logic(){
        coordinatorDAO = new CoordinatorDAO();
    }
    public List<Event> getEvents() {
        return coordinatorDAO.getEventList();
    }


    public void createEvent(Event event) {
        coordinatorDAO.createEvent(event);
    }
    public void editEvent(Event event) {coordinatorDAO.editEvent(event);}
    public boolean deleteEvent(Event event) {return coordinatorDAO.deleteEvent(event.getId());}

    public void createTicket(Ticket ticket) {
        coordinatorDAO.createTicket(ticket);
    }
    public boolean deleteTicket(Ticket ticket) {
        return coordinatorDAO.deleteTicket(ticket);
    }

    public List<Ticket> getTickets() {
        return coordinatorDAO.getAllTickets();
    }


}
