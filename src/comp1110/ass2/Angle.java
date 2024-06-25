package comp1110.ass2;

/**
 * Possible angles used for rotating pieces
 */
public enum Angle {
    DEG_0(0), DEG_270(270), DEG_180(180), DEG_90(90);

    // value of the angle in the form of an int (primitive Integer)
    public final int value;

    /**
     * Constructor: creates a new instance of the Angle class
     *
     * @param angle the angle in Degree
     */
    Angle(int angle) {
        this.value = angle;
    }

    /**
     * Convert value in degrees into a cardinal direction
     *
     * @param value in degrees must be non-negative, can be over 360
     * @return new cardinal direction if angle divisible by 90, else null
     */
    public static Angle getAngleFromValue(int value) {
        assert value >= 0;
        value = value % 360;
        if (value % 90 != 0) {
            return null;
        }
        for (Angle direction : Angle.values()) {
            if (direction.value == value) {
                return direction;
            }
        }
        return null;
    }

    public static Angle getAngleForOrientation(char orientation) {
        return switch (orientation) {
            case 'N' -> DEG_0;
            case 'E' -> DEG_90;
            case 'S' -> DEG_180;
            case 'W' -> DEG_270;
            default -> throw new IllegalArgumentException("Invalid orientation: " + orientation);
        };
    }

    /**
     * Adds this angle to another angle
     *
     * @param other the other angle
     * @return new angle that is the sum of both
     */
    public Angle add(Angle other) {
        return getAngleFromValue(this.value + other.value);
    }

    public int getValue() {
        return this.value;
    }

    public double getRad() {
        return Math.toRadians(value);
    }

    /**
     * Converts Angle object to a string which can be printed out
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Angle{" + "value=" + value + '}';
    }
}