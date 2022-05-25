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
import org.tinylog.Logger;
import repository.GameStateRepository;

public class FirstController {
    @FXML
    private TextField playerName;

    @FXML
    private void newGame(ActionEvent event) throws IOException{
        BoardGameController boardGameController=new BoardGameController();
        String name=playerName.getText();
        boardGameController.setPlayerName(name);
        switchScene(event);
    }

    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        Logger.info("Switching to the Board.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Board.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleExit(ActionEvent event) {
        Logger.info("Exiting...");
        Platform.exit();
    }

    @FXML
    public void handleLoadGame(ActionEvent actionEvent) throws IOException {
        var repository=new GameStateRepository();
        BoardGameController boardGameController=new BoardGameController();
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
