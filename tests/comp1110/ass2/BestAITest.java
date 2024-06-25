package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BestAITest {

    @Test
    public void testBestDirection() {
        // Because we could have multiple bestDirections
        for (int i = 0; i < 100; i++) {
            String gameState = "Pc00001oPy00001oPp12001iPr00001oA13SBn00p52p52r14y17r51n00p45p45p22p22y17p69p69r12p44p44r24r24r41p33n00p04c13p08p59r65r65p09p09c13p60r42r59r59y27p49p53p60r50r68r68n00p49p53p10r48r48p05";  //

            Marrakech game = new Marrakech(gameState);
            game.bestDirection(1);
            Set<String> expectedAssamAfterBestDirection = new HashSet<String>(Arrays.asList("A13S", "A13W", "A13E"));
            String newAssam = game.assam.toString();

            assertTrue(expectedAssamAfterBestDirection.contains(newAssam), "The calculated best direction was not among the expected directions.");
        }

    }

    //
    @Test
    public void testBestPlacement() {
        // Because we could have multiple bestPlacements
        for (int i = 0; i < 100; i++) {
            String gameState = "Pc03003iPy02503iPp04703iPr01804iA22NBn00n00r00r09y30y34n00c22c10r33y41y41y34p12c22y25r33c34r36c31c41p02y25c39c39y35n00c41n00p14p21r35r35y16y16n00c23c23n00p37p39p39n00r03r03n00p37y27c20";  //
            Marrakech game = new Marrakech(gameState);

            IntPair[][] expectedPlacements = {
                    {new IntPair(0, 2), new IntPair(1, 2)},
                    {new IntPair(1, 2), new IntPair(1, 3)}};
            IntPair[] bestPlacement = game.bestPlacement(1);
            boolean matchFound = false;
            for (IntPair[] expected : expectedPlacements) {
                if (arraysEqual(expected, bestPlacement)) {
                    matchFound = true;
                    break;
                }
            }

            assertTrue(matchFound, "The calculated best placement did not match any of the expected placements.");
        }

    }

    public static boolean arraysEqual(IntPair[] arr1, IntPair[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }
}
