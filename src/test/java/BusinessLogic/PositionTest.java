package BusinessLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position1=new Position(0,1);
    Position position2=new Position(0,1);
    Position position3=new Position(1,1);

    @Test
    void equals() {
        assertTrue(position1.equals(position2));
        assertTrue(position1.equals(position1));
        assertFalse(position2.equals(position3));
        assertFalse(position1.equals(position3));
    }
}