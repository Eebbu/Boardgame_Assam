package comp1110.ass2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {

    private static int playerCount = 0;
    private int playerId;
    private Color color;
    private int dirhams = 30;
    private int rugsRemaining = 15;
    private boolean inGame = true;
    private int score = dirhams;
    private boolean isComputer = false;

    private static final Set<Color> usedColors = new HashSet<>();
    private static final Random random = new Random();

    public Player(Color color) {
        this.playerId = playerCount % 4;
        playerCount++;
        this.color = color;
        usedColors.add(this.color);
    }

    public Player(String playerString) {
        if (playerString.startsWith("P")) {
            this.playerId = playerCount % 4 + 1;
            playerCount++;
            this.color = Color.fromChar(playerString.charAt(1));
            this.dirhams = Integer.parseInt(playerString.substring(2, 5));
            this.rugsRemaining = Integer.parseInt(playerString.substring(5, 7));
            this.inGame = playerString.charAt(7) == 'i'; // Check if 'i' for in-game
        }  // Handle invalid input or throw an exception

    }

    public void Pay(Player recipient, int amount) {

        if (amount >= dirhams) {
            recipient.dirhams += this.dirhams;
            this.dirhams = 0;
            this.setGameOver();
        } else {
            recipient.dirhams += amount;
            dirhams -= amount;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameOver() {

        System.out.println("player " + color + " runs out of his/her money");
        this.inGame = false;
        this.color = Color.NOCOLOR;
    }

    public boolean isInGame() {
        return this.inGame;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public static void resetPlayerCount() {
        playerCount = 0;
        usedColors.clear();
    }

    public void setCom() {
        this.isComputer = true;
    }

    public static Color getRandomColor() {
        usedColors.add(Color.NOCOLOR);
        Color[] availableColors = Color.values();
        while (true) {
            Color randomColor = availableColors[random.nextInt(availableColors.length)];
            if (!usedColors.contains(randomColor)) {
                return randomColor;
            }
        }
    }

    public int getPlayId() {
        return this.playerId;
    }

    public Color getColor() {
        return this.color;
    }

    public int getDirhams() {
        return this.dirhams;
    }

    public int getRugsRemaining() {
        return this.rugsRemaining;
    }

    public void reduceRugsRemaining() {
        if (this.rugsRemaining > 0) {
            this.rugsRemaining -= 1;
        }
    }

    @Override
    public String toString() {
        if (this.isInGame()) {
            return "P" + this.color.value + String.format("%03d", dirhams) + String.format("%02d", rugsRemaining) + "i";
        }
        return "P" + this.color.value + String.format("%03d", dirhams) + String.format("%02d", rugsRemaining) + "o";

    }
}

