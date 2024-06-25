package comp1110.ass2;

import java.util.ArrayList;

/**
 * The Tile class defines the state of a particular position on the board
 */

public class Tile {
    private final ArrayList<Rug> placedRugs;

    /**
     * Constructor: creates a new empty Tile
     */
    public Tile() {
        placedRugs = new ArrayList<>();
    }

    /**
     * getter method for possiblePiece
     *
     * @return possiblePiece
     */
    public Rug getTopRug() {
        if (!placedRugs.isEmpty()) {
            return placedRugs.get(placedRugs.size() - 1);
        }
        return null;
    }

    public void addPlacedRugs(Rug rug) {
        this.placedRugs.add(rug);
    }

    /**
     * Converts Transform object to a string which can be printed out
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "";
    }
}
