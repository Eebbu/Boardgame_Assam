package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class AngleTest {

    @Test
    void testGetAngleFromValueForInvalidAngles() {
        int testTimes = RandomGenerator.getDefault().nextInt(9999);
        int[] invalidAngles = new int[testTimes];
        for (int i = 0; i < testTimes; i++) {
            invalidAngles[i] = generateRandomAngle();
        }
        for (int angle : invalidAngles) {
            assertNull(Angle.getAngleFromValue(angle), "Failed for angle: " + angle);
        }
    }

    public static int generateRandomAngle() {
        Random random = new Random();
        int angle;

        do {
            angle = random.nextInt(361);
        } while (angle % 90 == 0);

        return angle;
    }
}