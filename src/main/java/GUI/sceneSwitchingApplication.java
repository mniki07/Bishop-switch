package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.tinylog.Logger;

import java.util.Objects;

public class sceneSwitchingApplication extends Application {
   // private static final Logger logger = LogManager.getLogger();


    @Override
    public void start(Stage stage) throws Exception {
        Logger.info("Show the homepage");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/homePage.fxml")));
        stage.setTitle("Bishop switch");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
