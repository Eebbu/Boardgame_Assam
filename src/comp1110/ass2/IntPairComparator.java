package comp1110.ass2;

import java.util.Comparator;

public class IntPairComparator implements Comparator<IntPair> {
    @Override
    public int compare(IntPair pair1, IntPair pair2) {
        if (pair1.getX() == pair2.getX()) {
            if (pair1.getY() == pair2.getY()) {
                return Integer.compare(pair1.getX(), pair2.getX());
            }
            return Integer.compare(pair1.getY(), pair2.getY());
        }
        return Integer.compare(pair1.getX(), pair2.getX());
    }

    @Override
    public Comparator<IntPair> reversed() {
        return Comparator.super.reversed();
    }
}
