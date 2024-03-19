package dk.onefreebeer.model;

import dk.onefreebeer.be.Event;
import dk.onefreebeer.be.Ticket;
import dk.onefreebeer.bll.Logic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private Logic logic;

    public Model(){
        this.logic = new Logic();
    }

    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();


    public ObservableList<Event> getEvents(){
        updateEventList();
        return eventList;
    }

    public void addEventToList(Event event){
        eventList.add(event);
    }

    public void updateEventList(){
        this.eventList.setAll(logic.getEvents());
    }


    @Override
    public String toString() {
        return "Model{" +
                "logic=" + logic +
                ", eventList=" + eventList +
                '}';
    }

    public void createEvent(Event event){
        logic.createEvent(event);
    }

    public void createTicket(Ticket ticket){
        logic.createTicket(ticket);
    }

    public ObservableList<Ticket> getTickets(){
        updateTicketList();
        return tickets;
    }

    private void updateTicketList(){
        this.tickets.setAll(logic.getTickets());
    }
}
