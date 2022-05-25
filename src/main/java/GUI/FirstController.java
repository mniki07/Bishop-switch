package GUI;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.GameStateRepository;

public class FirstController {
    private static final Logger logger = LogManager.getLogger();

    @FXML
    private TextField playerName;

    @FXML
    private void newGame(ActionEvent event) throws IOException{
        boardGameController boardGameController=new boardGameController();
        String name=playerName.getText();
        boardGameController.setPlayerName(name);
        switchScene(event);
    }

    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        logger.info("Switching to the Board.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleExit(ActionEvent event) {
        logger.info("Exiting...");
        Platform.exit();
    }

    @FXML
    public void handleLoadGame(ActionEvent actionEvent) throws IOException {
        var repository=new GameStateRepository();
        boardGameController boardGameController=new boardGameController();
        try {
            repository.loadFromFile(new File("SavedGame.json"));
            var gameState=repository.findAll();
            boardGameController.setColor(gameState.get(0).getColor());
            boardGameController.setPlayerName(gameState.get(0).getPlayerName());
            boardGameController.setBoardBusinessLogic(gameState.get(0).getBoard());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switchScene(actionEvent);
    }
}
