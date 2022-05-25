package GUI;

import BusinessLogic.Bishop;
import BusinessLogic.Board;
import BusinessLogic.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.tinylog.Logger;
import repository.GameState;
import repository.GameStateRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class boardGameController {
    //private static final Logger logger = LogManager.getLogger();

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;
    private static Bishop.Color color;

    private static String playerName;

    public void setPlayerName(String newPlayerName){
        playerName=newPlayerName;
    }
    public void setColor(Bishop.Color newColor){
        color=newColor;
    }
    public void setBoardBusinessLogic(Board newBoardBusinessLogic){
        boardBusinessLogic=newBoardBusinessLogic;
    }

    public void setDefaultValue(){
        if(color==null){
            color= Bishop.Color.BLACK;
            boardBusinessLogic=new Board();
        }
    }
    @FXML
    private GridPane board;

    @FXML
    private void initialize() {
        setDefaultValue();
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void createBoard() {
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int column = 0; column < board.getColumnCount(); column++) {
                var square = createSquare();
                if (row%2==0 && column%2==0 || row%2==1 && column%2==1){
                    square.getStyleClass().add("light");
                }
                else{
                    square.getStyleClass().add("dark");
                }
                board.add(square, column, row);
            }
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private static Board boardBusinessLogic;
    private void createPieces() {
        for (int row = 0; row < 5; row++) {
            for(int column=0; column<4; column++) {
                Position temporaryPosition = new Position(row, column);
                var bishop = boardBusinessLogic.getBishop(temporaryPosition);
                if (bishop!=null){
                    if (bishop.getColor()== Bishop.Color.WHITE){
                        Image image = new Image("whiteBishop.png");
                        ImageView whiteBishop = new ImageView(image);
                        getSquare(temporaryPosition).getChildren().add(whiteBishop);
                    }
                    else{
                        Image image = new Image("blackBishop.png");
                        ImageView blackBishop = new ImageView(image);
                        getSquare(temporaryPosition).getChildren().add(blackBishop);
                    }
                }
                else{
                    getSquare(temporaryPosition).getChildren().clear();
                }
            }
        }
    }


    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
    }
    Position fromPosition;

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                boolean possibleFrom=false;
                for (var selectablePosition:selectablePositions) {
                    if (selectablePosition.equals(position)) {
                        possibleFrom=true;
                    }
                }
                if(possibleFrom){
                    selectPosition(position);
                    alterSelectionPhase();
                    fromPosition=position;
                    color=boardBusinessLogic.getBishop(position).getColor();
                }

            }
            case SELECT_TO -> {
                boolean possibleTo=false;
                for (var selectablePosition:selectablePositions) {
                    if (selectablePosition.equals(position)) {
                        possibleTo=true;
                    }
                }
                if(possibleTo){
                    boardBusinessLogic.move(fromPosition, position);
                    createPieces();
                    deselectSelectedPosition();
                    if (isWin(boardBusinessLogic)){
                        Logger.info("Won the game");
                        winState();
                    }
                    alterSelectionPhase();
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        if (selected!=null){
            var bishop=boardBusinessLogic.getBishop(selected);
            if (bishop!=null){
                selectablePositions.addAll(bishop.listOfValidMoves(selected,boardBusinessLogic));
            }
        }
        else{
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 4; column++) {
                    Position temporaryPosition=new Position(row,column);
                    if (boardBusinessLogic.getBishop(temporaryPosition)!=null){
                        if (color!=boardBusinessLogic.getBishop(temporaryPosition).getColor()) {
                            if (boardBusinessLogic.getBishop(temporaryPosition).listOfValidMoves(temporaryPosition,boardBusinessLogic).size()!=0) {
                                selectablePositions.add(temporaryPosition);
                            }
                        }
                    }
                }
            }
        }
    }

    private void showSelectablePositions() {
        if (selectablePositions.size()==0){
            Logger.info("Lose the game, because there is no possible moves");
            gameOver();
        }
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }

    }
    private void gameOver(){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No more possible moves");
        alert.setHeaderText("There is no more possible moves.");
        alert.setContentText( "Please, start a new game.");
        alert.showAndWait().ifPresent(okay-> {if (okay== ButtonType.OK){
            System.out.println();
        }});
    }
    private void winState(){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Win");
        alert.setHeaderText("You Won!");
        alert.setContentText( "Congratulations!");
        alert.showAndWait().ifPresent(okay-> {if (okay== ButtonType.OK){
            System.out.println();
        }});
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            Position temporaryPosition=new Position(GridPane.getRowIndex(child),GridPane.getColumnIndex(child));
            if (temporaryPosition.equals(position)) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }
    private boolean isWin(Board board){
        Position position00=new Position(0,0);
        Position position01=new Position(0,1);
        Position position02=new Position(0,2);
        Position position03=new Position(0,3);
        Position position40=new Position(4,0);
        Position position41=new Position(4,1);
        Position position42=new Position(4,2);
        Position position43=new Position(4,3);
        if (board.getBishop(position00)==null || board.getBishop(position01)==null ||board.getBishop(position02)==null
                ||board.getBishop(position03)==null || board.getBishop(position40)==null || board.getBishop(position41)==null
                ||board.getBishop(position42)==null || board.getBishop(position43)==null){
            return false;

        }
        return board.getBishop(position00).getColor() == Bishop.Color.WHITE && board.getBishop(position01).getColor() == Bishop.Color.WHITE &&
                board.getBishop(position02).getColor() == Bishop.Color.WHITE && board.getBishop(position03).getColor() == Bishop.Color.WHITE &&
                board.getBishop(position40).getColor() == Bishop.Color.BLACK && board.getBishop(position41).getColor() == Bishop.Color.BLACK &&
                board.getBishop(position42).getColor() == Bishop.Color.BLACK && board.getBishop(position43).getColor() == Bishop.Color.BLACK;
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Logger.debug("Exiting...");
        Platform.exit();
    }

    @FXML
    public void handleSaveGame(ActionEvent actionEvent) {
        var repository=new GameStateRepository();
        GameState gameState=new GameState(playerName,color,boardBusinessLogic);
        repository.add(gameState);

        try {
            repository.saveToFile(new File("SavedGame.json"));
            Logger.info("Game saved.");
        }
        catch (IOException e){
            Logger.warn("Game might not be saved.");
        }
    }
    private FirstController firstController=new FirstController();
    @FXML
    public void handleNewGame(ActionEvent event) throws IOException {
        color=null;
        firstController.switchScene(event);
    }
}
