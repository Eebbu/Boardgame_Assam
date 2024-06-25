package comp1110.ass2;

import java.util.Objects;

/**
 * This class defines a transformation that is defined by a translation and a rotation
 * See the Readme for more information!
 */

public class Transform {

    private final IntPair translation;

    // rotation applies clock-wise
    private final Angle rotation;

    /**
     * Constructor for the transform class
     *
     * @param translation See the Readme for more information
     * @param rotation    See the Readme for more information
     */
    public Transform(IntPair translation, Angle rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Transform(int rotation) {
        this.translation = null;
        if (rotation == 0) {
            this.rotation = Angle.getAngleFromValue(rotation);
        } else if (rotation % 90 != 0 || rotation % 360 == 180) {
            this.rotation = null;
        } else {
            this.rotation = Angle.getAngleFromValue(rotation);
        }
    }

    /**
     * getter method for rotation
     *
     * @return rotation
     */
    public Angle getRotation() {
        return rotation;
    }

    /**
     * Boilerplate method to ensure that .equals() method compares two objects correctly
     *
     * @param o other object that might be an IntPair
     * @return true if this object is equal/equivalent to the other object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Objects.equals(translation, transform.translation) && rotation == transform.rotation;
    }

    /**
     * Boilerplate method to ensure that an array of Transform can be sorted
     *
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(translation, rotation);
    }

    /**
     * Converts Transform object to a string which can be printed out
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Transform{" + "translation=" + translation + ", rotation=" + rotation + '}';
    }
}









