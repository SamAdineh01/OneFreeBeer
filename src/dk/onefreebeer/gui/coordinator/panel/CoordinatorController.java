package dk.onefreebeer.gui.coordinator.panel;


import dk.onefreebeer.be.Event;
import dk.onefreebeer.be.Ticket;
import dk.onefreebeer.gui.coordinator.event.CreateEvent;
import dk.onefreebeer.gui.coordinator.event.EditEvent;
import dk.onefreebeer.gui.coordinator.ticket.CreateTicket;
import dk.onefreebeer.model.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private TableColumn<Event, String> eventDate;
    @FXML
    private TableColumn<Event, String> eventTime;


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
    private Event eventToEdit;

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
    @FXML
    public void onEditEvent(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/onefreebeer/gui/coordinator/event/EditEventView.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        EditEvent editEvent = loader.getController();
        editEvent.setEventToEdit(eventToEdit);
    }
    @FXML
    private void onDeleteEvent(ActionEvent actionEvent) {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Event");
            alert.setContentText("Are you sure you want to delete this event?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean deletionSuccessful = model.deleteEvent(selectedEvent);
                    if (deletionSuccessful) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Event deleted successfully");
                        successAlert.showAndWait();
                        refreshEventList();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Failed to delete event. Make sure to delete tickets associated with this event!");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert noEventSelectedAlert = new Alert(Alert.AlertType.ERROR);
            noEventSelectedAlert.setTitle("Error");
            noEventSelectedAlert.setHeaderText(null);
            noEventSelectedAlert.setContentText("Please select an event to delete.");
            noEventSelectedAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        eventNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        eventLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventTime.setCellValueFactory(new PropertyValueFactory<>("time"));

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

    public void addToTableView(Event event) {
        eventsTable.getItems().add(event);
    }

    public void refreshEventList() {
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
    @FXML
    private void onDeleteTicket() {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();

        if (selectedTicket != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Ticket");
            alert.setContentText("Are you sure you want to delete this ticket?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean deletionSuccessful = model.deleteTicket(selectedTicket);
                    if (deletionSuccessful) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Ticket deleted successfully");
                        successAlert.showAndWait();
                        refreshTicketList();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Failed to delete ticket");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert noTicketSelectedAlert = new Alert(Alert.AlertType.ERROR);
            noTicketSelectedAlert.setTitle("Error");
            noTicketSelectedAlert.setHeaderText(null);
            noTicketSelectedAlert.setContentText("Please select a ticket to delete.");
            noTicketSelectedAlert.showAndWait();
        }
    }

    private void refreshTicketList() {
        tickets.setAll(model.getTickets());
    }
}

