package comp1110.ass2;

/**
 * A Pair of ints (primitive Integers) that is used for variety of purposes in this assignment
 */
public class IntPair {
    // x int
    private int x;
    // y int
    private int y;

    /**
     * Constructor to create an instance of IntPair
     *
     * @param x coordination x
     * @param y coordination y
     */
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntPair(String IntPairString) {
        this.x = Integer.parseInt(IntPairString.substring(0, 1));
        this.y = Integer.parseInt(IntPairString.substring(1, 2));
    }

    public IntPair add(IntPair intPair) {
        return new IntPair(this.x + intPair.x, this.y + intPair.y);
    }


    /**
     * getter method for x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter method for y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Creates an IntPair from a string encoding
     *
     * @param IntPairString a String of two coordination for example "1234" means [1,2] & [3,4]
     * @return new IntPair[] object
     */
    public static IntPair[] constructFromString(String IntPairString) {
        IntPair[] Intpairarray = new IntPair[2];
        Intpairarray[0] = new IntPair(Integer.parseInt(IntPairString.substring(0, 1)), Integer.parseInt(IntPairString.substring(1, 2)));
        Intpairarray[1] = new IntPair(Integer.parseInt(IntPairString.substring(2, 3)), Integer.parseInt(IntPairString.substring(3, 4)));
        return Intpairarray;
    }


    public boolean isAdjacentTo(IntPair intPair) {
        int i = Math.abs(this.x - intPair.x) + Math.abs(this.y - intPair.y);
        return i == 1;
    }


    /**
     * Boilerplate method to ensure that .equals() method compares two objects correctly
     *
     * @param o other object that might be an IntPair
     * @return true if this object is equal/equivalent to the other object
     */
    @Override
    public boolean equals(Object o) {
        return this.x == ((IntPair) o).x && this.y == ((IntPair) o).y;
    }

    /**
     * Boilerplate method to ensure that an array of IntPair can be sorted
     *
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Converts IntPair object to a string which can be printed out
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return x + Integer.toString(y);
    }
}