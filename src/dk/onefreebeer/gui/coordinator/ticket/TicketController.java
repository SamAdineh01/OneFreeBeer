package dk.onefreebeer.gui.coordinator.ticket;

import dk.onefreebeer.be.Ticket;
import dk.onefreebeer.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.glxn.qrgen.javase.QRCode;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TicketController implements Initializable {

    @FXML
    private Label eventName;

    @FXML
    private Label eventNote;

    @FXML
    private Label ticketType;


    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label date;

    @FXML
    private ImageView qrCode;
    private Model model;

    private Ticket ticket;


    public TicketController(){
        this.model = new Model();
        this.ticket = new Ticket();
    }
    public void setModel(Model model) {
        this.model = model;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void loadPrintedTicket(){
        eventName.setText(ticket.getEvent().getTitle());
        eventNote.setText(ticket.getEvent().getNote());
        endDate.setText(ticket.getEvent().getDate());
        date.setText(ticket.getEvent().getTime());
        ticketType.setText(ticket.getType());
        generateQR(ticket);

    }


    private void generateQR(Ticket ticket){
        ByteArrayOutputStream out = QRCode.from(ticket.getUuid().toString()).stream();
        Image fxImage = new Image(new ByteArrayInputStream(out.toByteArray()));
        qrCode.setImage(fxImage);
    }
}
