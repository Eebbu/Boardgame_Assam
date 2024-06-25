package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssamRotateTest {

    @Test
    // Test rotating Assam 90 degrees
    public void testRotateAssam90Degrees() {
        Marrakech marrakech = new Marrakech();
        marrakech.assam = new Assam(new IntPair(3, 4), Angle.DEG_0);  // Assam initially facing North at position (3, 4)
        int rotation = 90;
        Transform newTransform = new Transform(rotation);

        marrakech.rotateAssam(newTransform);
        String rotatedAssam = marrakech.assam.toString();
        // Expected result: Assam should be facing East (E)
        Assertions.assertEquals(rotatedAssam,"A34E","Expected A34E");
    }

    // Test rotating Assam 270 degrees
    @Test
    public void testRotateAssam270Degrees() {
        Marrakech marrakech = new Marrakech();
        marrakech.assam = new Assam(new IntPair(2, 2), Angle.DEG_90);  // Assam initially facing North at position (3, 4)
        int rotation = 270;
        Transform newTransform = new Transform(rotation);

        marrakech.rotateAssam(newTransform);
        String rotatedAssam = marrakech.assam.toString();
        // Expected result: Assam should be facing North(N)
        Assertions.assertEquals(rotatedAssam,"A22N","Expected A22N");
    }

    // Test rotating Assam with an illegal rotation
    @Test
    public void testRotateAssamIllegalRotation() {

        Marrakech marrakech = new Marrakech();
        marrakech.assam = new Assam(new IntPair(1, 1), Angle.DEG_180);  // Assam initially facing North at position (3, 4)
        int rotation = 45;  // Illegal rotation (not 90 degrees)
        Transform newTransform = new Transform(rotation);

        marrakech.rotateAssam(newTransform);
        String rotatedAssam = marrakech.assam.toString();
        // Expected result: Assam should remain unchanged because of an illegal rotation
        Assertions.assertEquals(rotatedAssam,"A11S","Expected A11S");
    }

}