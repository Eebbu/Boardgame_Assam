package comp1110.ass2;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntPairCompareTest {
    @Test
    public void testCompareEqualPairs() {
        IntPairComparator comparator = new IntPairComparator();
        IntPair pair1 = new IntPair(1, 2);
        IntPair pair2 = new IntPair(1, 2);
        int result = comparator.compare(pair1, pair2);
        assertEquals(0, result);
    }

    @Test
    public void testCompareEqualXValues() {
        IntPairComparator comparator = new IntPairComparator();
        IntPair pair1 = new IntPair(1, 2);
        IntPair pair2 = new IntPair(1, 3);
        int result = comparator.compare(pair1, pair2);
        assertEquals(-1, result);
    }

    @Test
    public void testCompareEqualYValues() {
        IntPairComparator comparator = new IntPairComparator();
        IntPair pair1 = new IntPair(1, 2);
        IntPair pair2 = new IntPair(2, 2);
        int result = comparator.compare(pair1, pair2);
        assertEquals(-1, result);
    }

    @Test
    public void testCompareDifferentPairs() {
        IntPairComparator comparator = new IntPairComparator();
        IntPair pair1 = new IntPair(1, 2);
        IntPair pair2 = new IntPair(3, 4);
        int result = comparator.compare(pair1, pair2);
        assertEquals(-1, result);
    }

    @Test
    public void testCompareReversed() {
        IntPairComparator comparator = new IntPairComparator();
        IntPair pair1 = new IntPair(1, 2);
        IntPair pair2 = new IntPair(3, 4);
        Comparator<IntPair> reversedComparator = comparator.reversed();
        int result = reversedComparator.compare(pair1, pair2);
        assertEquals(1, result);
    }

    @Test
    public void testCompareMultiplePairs() {
        IntPairComparator comparator = new IntPairComparator();
        List<IntPair> pairs = Arrays.asList(
                new IntPair(1, 2),
                new IntPair(3, 4),
                new IntPair(1, 1),
                new IntPair(2, 2)
        );

        pairs.sort(comparator);
        assertEquals(new IntPair(1, 1), pairs.get(0));
        assertEquals(new IntPair(1, 2), pairs.get(1));
        assertEquals(new IntPair(2, 2), pairs.get(2));
        assertEquals(new IntPair(3, 4), pairs.get(3));
    }
}