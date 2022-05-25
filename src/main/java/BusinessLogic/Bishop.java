package BusinessLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the bishops.
 */
public class Bishop {
    public enum Color {
        BLACK, WHITE
    }

    private Position position;
    private Color color;

    /**
     * Creates a bishop.
     * @param color    is the color component of the piece
     * @param position is the position component of the piece
     */
    public Bishop(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Decides whether the move is diagonal or not.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @return true when the move is valid, otherwise false
     */
    private boolean isDiagonalMove(Position from, Position to) {
        return Math.abs(to.getRow() - from.getRow()) == Math.abs(to.getColumn() - from.getColumn()) && from.getRow() != to.getRow();
    }

    /**
     * Decides whether the Bishops step over each other or not.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @param board is the actual state of the board
     * @return true, when the Bishop would step over another Bishop, otherwise false
     */
    private boolean isAJump(Position from, Position to, Board board) {
        if (from.getRow() > to.getRow()) {
            if (from.getColumn() > to.getColumn()) {
                return isValidUpLeft(from, to, board);
            } else {
                return isValidUpRight(from, to, board);
            }
        } else {
            if (from.getColumn() > to.getColumn()) {
                return isValidDownLeft(from, to, board);
            } else {
                return isValidDownRight(from, to, board);
            }
        }
    }

    /**
     * Decides whether the Bishop moves to the up-left direction, that it's step over another piece or not.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @param board is the actual state of the board
     * @return true, when the piece do not a step over another piece, otherwise false
     */
    private boolean isValidUpLeft(Position from, Position to, Board board) {
        for (int row = from.getRow()-1; row > to.getRow(); row--) {
            for(int column=from.getColumn()-1; column>to.getColumn();column--){
                if (isDiagonalMove(from, new Position(row,column))) {
                    if (board.getBishop(new Position(row, column)) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Decides whether the Bishop moves to the up-right direction, that it's step over another piece or not.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @param board is the actual state of the board
     * @return true, when the piece do not a step over another piece, otherwise false
     */
    private boolean isValidUpRight(Position from, Position to, Board board) {
        for (int row = from.getRow()-1; row > to.getRow(); row--) {
            for (int column = from.getColumn()+1; column < to.getColumn(); column++) {
                if  (isDiagonalMove(from, new Position(row,column))) {
                    if (board.getBishop(new Position(row, column)) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Decides whether the Bishop moves to the down-left direction, that it's step over another piece or not.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @param board is the actual state of the board
     * @return true, when the piece do not a step over another piece, otherwise false
     */
    private boolean isValidDownLeft(Position from, Position to, Board board) {
        for (int row = from.getRow()+1; row < to.getRow(); row++) {
            for (int column = from.getColumn()-1; column > to.getColumn(); column--) {
                if (isDiagonalMove(from, new Position(row, column))){
                    if(board.getBishop(new Position(row, column)) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Decides whether the Bishop moves to the down-right direction, that it's step over another piece or not.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @param board is the actual state of the board
     * @return true, when the piece do not a step over another piece, otherwise false
     */
    private boolean isValidDownRight(Position from, Position to, Board board) {
        for (int row = from.getRow()+1; row < to.getRow(); row++) {
            for(int column=from.getColumn()+1; column<to.getColumn();column++){
                if (isDiagonalMove(from, new Position(row,column))) {
                    if (board.getBishop(new Position(row, row)) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Decides whether the Bishop of the opposite color will hit the chosen Bishop which enters that position.
     * @param from is the position from where we want to move
     * @param to   is the position where we want to move
     * @param board is the actual state of the board
     * @return true, when it's a hit, otherwise false
     */
    private boolean isHittable(Position from, Position to, Board board){
        for(int row=0;row<5;row++){
            for(int column=0;column<4;column++){
                if(board.getBishop(new Position(row,column))!=null
                  && board.getBishop(new Position(row,column)).getColor()!= board.getBishop(from).getColor()){
                    if(isDiagonalMove(new Position(row,column),to)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Decides whether the move is diagonal and the piece cannot be knocked down by the enemy.
     * @param from        is the position from where we want to move
     * @param to          is the position where we want to move
     * @param actualBoard is the actual state of the board
     * @return true when the move is diagonal and the piece cannot be knocked down, otherwise false
     */
    private boolean isPossibleMove(Position from, Position to, Board actualBoard) {
       if(actualBoard.getBishop(from)!=null){
           if (actualBoard.getBishop(to)==null) {
               if (isDiagonalMove(from, to)) {
                   if(!isAJump(from,to,actualBoard)){
                       return !isHittable(from, to, actualBoard);
                   }
               }
           }
       }
        return false;
    }

    /**
     * Represents a list with the valid moves.
     * @param from is the position from where we want to move
     * @param actualBoard is the actual state of the board
     * @return a list which contains positions
     */

    public List<Position> listOfValidMoves(Position from, Board actualBoard) {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 4; column++) {
                Position temporaryPosition = new Position(row, column);
                if (isPossibleMove(from, temporaryPosition, actualBoard)) {
                    positions.add(temporaryPosition);
                }
            }
        }
        return positions;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }
}