package comp1110.ass2;

import java.util.Random;

class Die {
    public final Random random = new Random();
    private final int[] dieValue = {1, 2, 2, 3, 3, 4};

    //save the side which is going to show
    int currentDieState = 0;

    int roll() {
        int index = random.nextInt(6);
        currentDieState = index;
        return dieValue[index];
    }

    public int[] getDieValue() {
        return dieValue;
    }
}
