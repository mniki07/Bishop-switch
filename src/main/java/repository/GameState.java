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

    /**
     * The constructor of the GameState class.
     * @param newPlayerName the name of the player
     * @param newColor the color of the previous Bishop
     * @param newBoard the actual state of board
     */
    public GameState(String newPlayerName, Bishop.Color newColor, Board newBoard){
        playerName=newPlayerName;
        color=newColor;
        board=newBoard;
    }

    /**
     * Returns the name of the player
     * @return the name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the color of the player.
     * @return the color
     */
    public Bishop.Color getColor() {
        return color;
    }

    /**
     * Returns the board.
     * @return the board
     */
    public Board getBoard() {
        return board;
    }
}
