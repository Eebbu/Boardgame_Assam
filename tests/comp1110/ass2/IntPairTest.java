package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntPairTest {

    @Test
    void isAdjacentTo() {
        IntPair base = new IntPair(2,3);
        IntPair intPair1 = new IntPair(0, 5);
        IntPair intPair2 = new IntPair(1, 4);
        IntPair intPair3 = new IntPair(3, 2);
        IntPair intPair4 = new IntPair(7, 6);
        IntPair intPair5 = new IntPair(2, 2);
        assertFalse(base.isAdjacentTo(intPair1),"(2,3) is not adjacent to (0,5) ,but get true");
        assertFalse(base.isAdjacentTo(intPair2),"(2,3) is not adjacent to (1,4) ,but get true");
        assertFalse(base.isAdjacentTo(intPair3),"(2,3) is not adjacent to (3,2) ,but get true");
        assertFalse(base.isAdjacentTo(intPair4),"(2,3) is not adjacent to (7,6) ,but get true");
        assertTrue(base.isAdjacentTo(intPair5),"(2,3) is adjacent to (2,2) ,but get false");
    }

    @Test
    void testEquals() {
        IntPair base = new IntPair(2,3);
        IntPair intPair1 = new IntPair(0, 5);
        IntPair intPair2 = new IntPair(1, 4);
        IntPair intPair3 = new IntPair(3, 2);
        IntPair intPair4 = new IntPair(7, 6);
        IntPair intPair5 = new IntPair(2, 2);
        IntPair intPair6 = new IntPair(2, 3);
        assertNotEquals(base, intPair1, "(2,3) is not equal to (0,5) but get true");
        assertNotEquals(base, intPair2, "(2,3) is not equal to (1,4) but get true");
        assertNotEquals(base, intPair3, "(2,3) is not equal to (3,2) but get true");
        assertNotEquals(base, intPair4, "(2,3) is not equal to (7,6) but get true");
        assertNotEquals(base, intPair5, "(2,3) is not equal to (2,2) but get true");
        assertEquals(base, intPair6, "(2,3) is equal to (2,3) but get false");


    }
}