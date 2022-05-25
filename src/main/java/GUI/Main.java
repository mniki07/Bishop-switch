package GUI;

import javafx.application.Application;
import org.tinylog.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class Main {
    //private static final Logger logger = LogManager.getLogger();

    /**
     * Starts the application.
     * @param args are the command line arguments
     */
    public static void main(String[] args) {
        Logger.info("Application launch");
        Application.launch(sceneSwitchingApplication.class, args);
    }
}
