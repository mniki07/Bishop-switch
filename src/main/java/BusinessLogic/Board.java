package BusinessLogic;

import org.tinylog.Logger;

/**
 * Represents a 2D board with 5*4 size
 */
public class Board {
    private Bishop[][] board=new Bishop[5][4];

    /**
     * Creates a new board, with the pieces at the starting positions.
     */
    public Board(){
        for (int i=0;i<4;i++){
            board[0][i]=new Bishop(Bishop.Color.BLACK,
                    new Position(0,i));
        }
        for (int i=0;i<4;i++){
            board[4][i]=new Bishop(Bishop.Color.WHITE,
                    new Position(4,i));
        }
        Logger.debug("Made the board");
    }

    /**
     * Moves the Bishops on the board
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     */
    public void move(Position from, Position to){
        board[to.getRow()][to.getColumn()]=board[from.getRow()][from.getColumn()];
        board[to.getRow()][to.getColumn()].getPosition().setRow(to.getRow());
        board[to.getRow()][to.getColumn()].getPosition().setColumn(to.getColumn());
        board[from.getRow()][from.getColumn()]=null;
    }

    /**
     * Returns a Bishop.
     * @param position the position of the Bishop
     * @return a Bishop
     */
    public Bishop getBishop(Position position){
        return board[position.getRow()][position.getColumn()];
    }

}
