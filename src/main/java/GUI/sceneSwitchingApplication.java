package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class sceneSwitchingApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/homePage.fxml")));
        stage.setTitle("JavaFX Scene Switching");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
