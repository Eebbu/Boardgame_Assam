package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    @Test
    void roll() {
        Die die = new Die();
        int testTimes = RandomGenerator.getDefault().nextInt(9999);
        for (int i = 0; i < testTimes; i++) {
            int outcome = die.roll();
            assertTrue(outcome > 0 && outcome < 5);
            assertTrue(die.currentDieState >= 0 && die.currentDieState < 6);
            assertEquals(die.getDieValue()[die.currentDieState], outcome);
        }
    }
}