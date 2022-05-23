package BusinessLogic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {
    Position position=new Position(0,0);
    Bishop bishop=new Bishop(Bishop.Color.WHITE,position);
    Board board=new Board();


    @Test
    void validMoves() { /*
        Position fromPosition=new Position(0,0);
        Position toPosition=new Position(1,1);
        List<Position> moves= new ArrayList<Position>();
        moves.add(toPosition);
        Bishop bishop=new Bishop(Bishop.Color.WHITE,fromPosition);
        assertEquals(moves.size(), bishop.listOfValidMoves(fromPosition,board).size()); */
        assertEquals(bishop.listOfValidMoves(position,board), board.getBishop(new Position(0,0)).listOfValidMoves(position,board));

    }
}