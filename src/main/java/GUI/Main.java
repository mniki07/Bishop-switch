package GUI;

import javafx.application.Application;
import org.tinylog.Logger;

public class Main {
    /**
     * Starts the application.
     * @param args are the command line arguments
     */
    public static void main(String[] args) {
        Logger.info("Application launch");
        Application.launch(sceneSwitchingApplication.class, args);
    }
}
