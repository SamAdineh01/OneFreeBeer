package dk.onefreebeer.gui.coordinator.ticket;

import dk.onefreebeer.be.Event;
import dk.onefreebeer.be.Ticket;
import dk.onefreebeer.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

public class CreateTicket implements Initializable {

    private Model model;
    @FXML
    private Menu eventMenu;
    @FXML
    private Menu typesMenu;


    private ObservableList<Event> events = FXCollections.observableArrayList();

    public CreateTicket(){
        this.model = new Model();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        events.addAll(model.getEvents());
        for (Event event: events){
            MenuItem menuItem = new MenuItem(event.getTitle());
            eventMenu.getItems().add(menuItem);
            menuItem.setOnAction(e ->{
                eventMenu.setText(menuItem.getText());
            });
        }
        for (MenuItem menuItem: typesMenu.getItems()){
            menuItem.setOnAction(event -> {
                typesMenu.setText(menuItem.getText());
            });
        }
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    private void createTicket() throws IOException {
        Ticket ticket = new Ticket();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/onefreebeer/gui/coordinator/ticket/Ticket.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        TicketController ticketController = loader.getController();
        ticketController.setModel(this.model);

        for (Event event: events) {
            if ((Objects.equals(event.getTitle(), eventMenu.getText()))) {
                ticket.setEvent(event);
                ticket.setType(typesMenu.getText());
                ticket.setUuid(UUID.randomUUID());
                ticketController.setTicket(ticket);

            }
        }

        try {
            model.createTicket(ticket);

        } catch (Exception e){
            e.getMessage();
        }


        ticketController.loadPrintedTicket();
    }
}
