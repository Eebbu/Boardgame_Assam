package comp1110.ass2;

/**
 * Class which defines the shape and position of each piece on the board.
 * <p>
 * Note that in the backend: there is no state in which a piece is off the board.
 */
public class Rug {

    // Defines the shape of the Rug
    private IntPair[] relativeSegmentPositions = new IntPair[]{new IntPair(0, 0), new IntPair(0, 1)};

    // color of the Rug
    private final Color color;

    // number of the Rug
    private static int count = 0;
    private final int id;
    //private int id = 0;


    /**
     * Constructor: creates a new instance of the Rug class from String
     *
     * @param rugString is a String contain7  characters
     *                  - The first character in the String corresponds to the colour character of a player present in the game
     *                  - The next two characters represent a 2-digit ID number
     *                  - The next 4 characters represent coordinates that are on the board
     */
    public Rug(String rugString) {

        this.color = Color.fromChar(rugString.charAt(0));
        this.id = Integer.parseInt(rugString.substring(1, 3));
        this.relativeSegmentPositions = IntPair.constructFromString(rugString.substring(3, 7));
    }

    public Rug(Color color, IntPair pair1, IntPair pair2) {
        this.color = color;
        this.id = count;
        count++;
        this.relativeSegmentPositions = new IntPair[]{pair1, pair2};
    }


    //this method is used to create simulated rugs in possible rug method. Avoid making id expansion.
    public Rug(Color color, IntPair pair1, IntPair pair2, int i) {
        this.color = color;
        this.id = count;
        this.relativeSegmentPositions = new IntPair[]{pair1, pair2};
    }

    public Rug(Color color) {
        this.color = color;
        this.id = count;
        count++;
    }


    /**
     * Retrieves the relative segment positions of the rug.
     *
     * @return An array of IntPair representing the relative segment positions.
     */
    public IntPair[] getRelativeSegmentPositions() {
        return relativeSegmentPositions;
    }

    /**
     * Sets the relative segment positions of the rug.
     *
     * @param intPairs An array of IntPair representing the new relative segment positions.
     */
    public void setRelativeSegmentPositions(IntPair[] intPairs) {
        this.relativeSegmentPositions = intPairs;
    }

    /**
     * Checks if the current rug is valid compared to the given rug.
     *
     * @param rug The rug to compare with.
     * @return True if the current rug is different from the given rug, otherwise false.
     */
    boolean isRugValid(Rug rug) {
        return this.color != rug.color || this.id != rug.id;
    }


    /**
     * @return absolute positions for the current piece (see readme for more info)
     */
    public IntPair[] getAbsolutePositions() {
        return calcNewAbsolutePositions();
    }

    public static IntPair[] calcNewAbsolutePositions() {
        return null;
    }

    /**
     * getter method for color
     *
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * getter method for id
     *
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Boilerplate method to ensure that .equals() method compares two objects correctly
     *
     * @param o other object that might be of type Piece
     * @return true if this object is equal/equivalent to the other object
     */
    @Override
    public boolean equals(Object o) {
        return false;
    }


    /**
     * Converts Piece object to a string which can be printed out
     *
     * @return string representation
     */
    @Override
//TODO supplement this method
    public String toString() {
        return color.value + String.format("%02d", id) + relativeSegmentPositions[0] + relativeSegmentPositions[1];
    }
}
