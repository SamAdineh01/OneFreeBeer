package dk.onefreebeer.gui.coordinator.event;
import dk.onefreebeer.be.Event;
import dk.onefreebeer.model.Model;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditEvent {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea noteField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField timeField;
    @FXML
    private DatePicker dateField;

    private Model model;
    private Event eventToEdit;

    public EditEvent() {
       // this.eventToEdit = eventToEdit;
        //this.model = model;
    }
    public Event getEventToEdit() {
        return eventToEdit;
    }
    public void setEventToEdit(Event eventToEdit) {
        this.eventToEdit = eventToEdit;
    }
    @FXML
    public void initialize() {

            titleField.setText(eventToEdit.getTitle());
            noteField.setText(eventToEdit.getNote());
            locationField.setText(eventToEdit.getLocation());
            timeField.setText(eventToEdit.getTime());
    }

    @FXML
    private void onUpdateButton() {

        eventToEdit.setTitle(titleField.getText());
        eventToEdit.setNote(noteField.getText());
        eventToEdit.setLocation(locationField.getText());
        eventToEdit.setDate(dateField.getValue().toString());
        eventToEdit.setTime(timeField.getText());
        model.updateEventList();
        Scene scene = (Scene) titleField.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        Scene scene = (Scene) titleField.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }


}