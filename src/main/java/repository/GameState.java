package repository;

import BusinessLogic.Bishop;
import BusinessLogic.Board;
import lombok.*;

@Builder
@Data
public class GameState {
    private String playerName;
    private Bishop.Color color;
    private Board board;

    public GameState(String newPlayerName, Bishop.Color newColor, Board newBoard){
        playerName=newPlayerName;
        color=newColor;
        board=newBoard;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Bishop.Color getColor() {
        return color;
    }

    public Board getBoard() {
        return board;
    }
}
