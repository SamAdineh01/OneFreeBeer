package dk.onefreebeer.gui.login;

import dk.onefreebeer.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView logo;

    public LoginController() {
      /*  Image image = new Image("data/logo1.jpg");
        logo.setImage(image);
        Cannot load image, keeps saying invalid file path
       */
    }
    @FXML
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (UserManager.isAdmin(username, password)) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/Admin/panel/AdminView.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else if (UserManager.isEC(username, password)) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/coordinator/panel/CoordinatorView.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}