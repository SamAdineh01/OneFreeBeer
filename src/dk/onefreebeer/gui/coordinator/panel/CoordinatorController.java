package dk.onefreebeer.gui.coordinator.panel;


import dk.onefreebeer.be.Event;
import dk.onefreebeer.be.Ticket;
import dk.onefreebeer.gui.coordinator.event.CreateEvent;
import dk.onefreebeer.gui.coordinator.ticket.CreateTicket;
import dk.onefreebeer.model.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private TableColumn<Event, String> eventId;
    @FXML
    private TableColumn<Event, String> eventTitle;
    @FXML
    private TableColumn<Event, String> eventNote;
    @FXML
    private TableColumn<Event, String> eventLocation;
    @FXML
    private TableColumn<Event, String> eventStart;
    @FXML
    private TableColumn<Event, String> eventEnd;
    @FXML
    private TableColumn<Event, String> eventDate;


    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketId;
    @FXML
    private TableColumn<Ticket, String> eventIdT;
    @FXML
    private TableColumn<Ticket, String> ticket_type;



    private ObservableList<Event> events = FXCollections.observableArrayList();

    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();


    private Model model;

    public CoordinatorController(){
        this.model = new Model();
    }


    @FXML
    private void onCreateEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/onefreebeer/gui/coordinator/event/CreateEventView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        CreateEvent createEvent = loader.getController();
        createEvent.setModel(this.model);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        eventNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        eventLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        eventEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        ticketId.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventIdT.setCellValueFactory(cellData -> {
            Event event = cellData.getValue().getEvent();
            return new SimpleStringProperty(event != null ? event.getTitle() : "");
        });
        ticket_type.setCellValueFactory(new PropertyValueFactory<>("type"));



        events.setAll(model.getEvents());
        eventsTable.setItems(model.getEvents());


        tickets.setAll(model.getTickets());
        ticketsTable.setItems(model.getTickets());




    }

    public void addToTableView(Event event){
        eventsTable.getItems().add(event);
    }

    public void refreshEventList(){
        this.events.setAll(model.getEvents());
    }

    @FXML
    private void onCreateTicket() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/onefreebeer/gui/coordinator/ticket/CreateTicketView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        CreateTicket createTicket = loader.getController();
        createTicket.setModel(this.model);

    }
}
