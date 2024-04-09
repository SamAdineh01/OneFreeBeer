package dk.onefreebeer.gui.login;

import dk.onefreebeer.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    HashMap<String, String> EcInfo = new HashMap<>();
    HashMap<String, String> AdminInfo = new HashMap<>();

    public void initialize() {
        AdminInfo.put("Radelc","Adminpass");
        AdminInfo.put("SamAdineh","Adminpassword");
        EcInfo.put("Rad01","pass");
        EcInfo.put("Sam","password");
    }

    @FXML
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (AdminInfo.containsKey(username) && AdminInfo.get(username).equals(password)) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/Admin/panel/AdminView.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        else if (EcInfo.containsKey(username) && EcInfo.get(username).equals(password)) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/coordinator/panel/CoordinatorView.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}