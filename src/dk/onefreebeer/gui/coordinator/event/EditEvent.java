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
    private TextField time;
    @FXML
    private DatePicker date;

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
        // Populate fields with event data
        titleField.setText(eventToEdit.getTitle());
        noteField.setText(eventToEdit.getNote());
        locationField.setText(eventToEdit.getLocation());
    }

    @FXML
    private void onUpdateButton() {

        eventToEdit.setTitle(titleField.getText());
        eventToEdit.setNote(noteField.getText());
        eventToEdit.setLocation(locationField.getText());
        eventToEdit.setDate(date.getValue().toString());
        //eventToEdit.setTime(time.getValue().toString);

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