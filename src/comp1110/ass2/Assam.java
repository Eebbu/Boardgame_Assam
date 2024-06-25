package comp1110.ass2;

import java.util.HashMap;

public class Assam {
    private Angle angle;

    private IntPair position;

    private char orientation; // 'N', 'E', 'S', or 'W'


    /**
     * Constructor that initializes Assam with a position and angle.
     *
     * @param position The initial position of Assam.
     * @param angle    The initial angle of Assam.
     */
    public Assam(IntPair position, Angle angle) {
        this.position = position;
        this.angle = angle;
        this.orientation = angleToOrientation(angle);
    }

    /**
     * Constructor that initializes Assam from a string representation.
     *
     * @param assamString The string representation of Assam.
     */
    public Assam(String assamString) {
        if (assamString.startsWith("A")) {
            this.position = new IntPair(Character.getNumericValue(assamString.charAt(1)), Character.getNumericValue(assamString.charAt(2)));
            this.angle = Angle.getAngleForOrientation(assamString.charAt(3));
            this.orientation = assamString.charAt(3);
        } else {
            // Handle invalid input or throw an exception
            throw new IllegalArgumentException("Invalid Assam string: " + assamString);
        }
    }

    /**
     * Sets the angle of Assam.
     *
     * @param angle The new angle for Assam.
     */
    public void setAngle(Angle angle) {
        this.angle = angle;
    }

    /**
     * Sets the position of Assam.
     *
     * @param position The new position for Assam.
     */
    public void setPosition(IntPair position) {
        this.position = position;
    }

    /**
     * Sets the orientation of Assam.
     *
     * @param orientation The new orientation for Assam.
     */
    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    /**
     * Retrieves the current angle of Assam.
     *
     * @return The current angle of Assam.
     */
    // get current angle of Assam
    public Angle getAngle() {
        return this.angle;
    }

    /**
     * Retrieves the current position of Assam.
     *
     * @return The current position of Assam.
     */
    public IntPair getPosition() {
        return this.position;
    }

    /**
     * Changes the state of Assam based on a new orientation.
     *
     * @param newOrientation The new orientation for Assam.
     */
    public void changeAssamState(char newOrientation) {
        this.orientation = newOrientation;
    }

    public char getOrientation() {
        return this.orientation;
    }

    //A HashMap, which the key is pre-move Assam String, the value is after-move Assam String
    private final HashMap<String, String> moveRule = new HashMap<>() {
        {
            put("A00N", "A10S");
            put("A20N", "A30S");
            put("A40N", "A50S");
            put("A60N", "A60W");
            put("A61E", "A62W");
            put("A63E", "A64W");
            put("A65E", "A66W");
            put("A66S", "A56N");
            put("A46S", "A36N");
            put("A26S", "A16N");
            put("A06S", "A06E");
            put("A05W", "A04E");
            put("A03W", "A02E");
            put("A01W", "A00E");
            put("A00W", "A01E");
            put("A02W", "A03E");
            put("A04W", "A05E");
            put("A06W", "A06N");
            put("A16S", "A26N");
            put("A36S", "A46N");
            put("A56S", "A66N");
            put("A66E", "A65W");
            put("A64E", "A63W");
            put("A62E", "A61W");
            put("A60E", "A60S");
            put("A50N", "A40S");
            put("A30N", "A20S");
            put("A10N", "A00S");
        }
    };

    /**
     * Moves Assam based on a given dice number.
     *
     * @param DiceNum The number on the dice.
     */
    public void preMove(int DiceNum) {
        while (DiceNum > 0) {
            actualMove();
            DiceNum--;
        }
    }

    /**
     * Actually moves Assam. If Assam is at the edge, it moves according to predefined rules; otherwise, it moves in its current direction.
     */
    private void actualMove() {
        if (this.IsAtEdge()) {
            String after = moveRule.get(this.toString());
            if (null != after) {
                setPosition(new IntPair(after.substring(1, 3)));
                setOrientation(after.charAt(3));
                this.angle = Angle.getAngleForOrientation(after.charAt(3));
            }
        } else {
            switch (this.orientation) {
                case 'N' -> this.position.setY(this.position.getY() - 1);
                case 'S' -> this.position.setY(this.position.getY() + 1);
                case 'E' -> this.position.setX(this.position.getX() + 1);
                case 'W' -> this.position.setX(this.position.getX() - 1);
            }
            this.angle = Angle.getAngleForOrientation(this.orientation);
        }

    }

    /**
     * Checks if Assam is at the edge of the game board.
     *
     * @return True if Assam is at the edge, otherwise false.
     */
    private boolean IsAtEdge() {
        if (this.position.getX() == 0 && this.orientation == 'W') return true;
        if (this.position.getX() == 6 && this.orientation == 'E') return true;
        if (this.position.getY() == 0 && this.orientation == 'N') return true;
        return this.position.getY() == 6 && this.orientation == 'S';
    }

    /**
     * Applies a transformation to Assam.
     *
     * @param newTransform The transformation to apply.
     */
    public void applyTransform(Transform newTransform) {
        if (newTransform.getRotation() == null) return;
        Angle newAngle = newTransform.getRotation();
        setAngle(this.angle.add(newAngle));
        changeAssamState(this.angleToOrientation(this.angle));
    }

    /**
     * Converts an angle to its corresponding orientation.
     *
     * @param angle The angle to convert.
     * @return The corresponding orientation.
     */
    private char angleToOrientation(Angle angle) {
        return switch (angle) {
            case DEG_0 -> 'N';
            case DEG_90 -> 'E';
            case DEG_180 -> 'S';
            case DEG_270 -> 'W';
        };
    }

    /**
     * Returns a string representation of Assam.
     *
     * @return A string representation of Assam.
     */
    @Override
    public String toString() {
        return "A" + position.toString() + orientation;
    }
}
