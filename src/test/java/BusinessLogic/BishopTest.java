package BusinessLogic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {
    Position positionWhite =new Position(4,0);
    Position positionBlack =new Position(0,2);
    Bishop bishop=new Bishop(Bishop.Color.WHITE, positionWhite);
    Bishop bishop2=new Bishop(Bishop.Color.BLACK, positionBlack);
    Board board=new Board();

    @Test
    void listOfValidMoves() {
        List<Position> positions=new ArrayList<>();
        var position1=new Position(3,1);
        positions.add(position1);
        var position2=new Position(3,2);
        positions.add(position2);
        var position3=new Position(1,1);
        positions.add(position3);
        var position4=new Position(1,3);
        positions.add(position4);
        assertTrue(bishop.listOfValidMoves(positionWhite,board).get(0).equals(positions.get(0)));
        assertFalse(bishop.listOfValidMoves(positionWhite,board).get(0).equals(positions.get(1)));
        assertTrue(bishop2.listOfValidMoves(positionBlack,board).get(0).equals(positions.get(2)));
        assertFalse(bishop2.listOfValidMoves(positionBlack,board).get(0).equals(positions.get(3)));
    }
}