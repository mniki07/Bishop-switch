package BusinessLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Position positionWhite =new Position(4,0);
    Position positionBlack =new Position(0,2);
    Bishop bishop=new Bishop(Bishop.Color.WHITE, positionWhite);
    Bishop bishop2=new Bishop(Bishop.Color.BLACK, positionBlack);
    Board board=new Board();

    @Test
    void move() {
        var position1=new Position(3,1);
        board.move(positionWhite,position1);
        assertTrue(board.getBishop(positionWhite)==null);
        assertTrue(board.getBishop(position1).getColor()== Bishop.Color.WHITE);
        assertFalse(board.getBishop(positionWhite)==bishop);
        var position2=new Position(1,1);
        board.move(positionBlack,position2);
        assertTrue(board.getBishop(positionBlack)==null);
        assertTrue(board.getBishop(position2).getColor()== Bishop.Color.BLACK);
        assertFalse(board.getBishop(positionBlack)==bishop2);
    }
}