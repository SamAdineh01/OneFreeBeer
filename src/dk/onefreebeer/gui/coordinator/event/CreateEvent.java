package dk.onefreebeer.gui.coordinator.event;

import dk.onefreebeer.be.Event;
import dk.onefreebeer.model.Model;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateEvent {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea noteField;
    @FXML
    private TextField locationField;
    @FXML
    private DatePicker date;
    @FXML
    private TextField time;

    private Model model;

    public CreateEvent(){
        this.model = new Model();
    }

    public void setModel(Model model){
        this.model = model;
    }


    @FXML
    private void onSubmitButton(){

        this.model.createEvent(new Event(titleField.getText(), noteField.getText(), locationField.getText(), date.getValue().toString(), time.getText()));
        this.model.updateEventList();

        Scene scene = (Scene) titleField.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    private void onCancel(){
        Scene scene = (Scene) titleField.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }



}
