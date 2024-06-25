package comp1110.ass2;

import java.text.DecimalFormat;
import java.util.*;

public class Marrakech {

    // The width of the board (left to right)
    public final static int BOARD_WIDTH = 7;

    // The height of the board (top to bottom)
    public final static int BOARD_HEIGHT = 7;

    // The x position of Assam starts
    public final static int DEFAULT_ASSAM_X = 3;

    // The y position of Assam starts
    public final static int DEFAULT_ASSAM_Y = 3;

    // the only die in the game
    final static Die gameDie = new Die();

    // The matrix of tiles representing the Marrakech board
    // For boardMatrix[x][y]:
    // x corresponds to the tile column, working left to right, and
    // y corresponds to the tile row, working top to bottom.
    public Tile[][] boardMatrix = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

    public ArrayList<Rug> rugs = new ArrayList<>();

    public Assam assam;

    private int playerNumber = 4;

    public Player[] players;

    public int currentPlayerIndex = 0;

    /**
     * @author : ChangLai Sun
     * Generate a new Marrakech game
     */
    public Marrakech() {
        populateBoard();
    }

    /**
     * @author : Team work
     * Generate a new Marrakech game with specific playerNumber
     *
     * @param playerNumber the total number in the game default by 4
     */
    public Marrakech(int playerNumber) {
        assam = new Assam(new IntPair(DEFAULT_ASSAM_X, DEFAULT_ASSAM_Y), Angle.DEG_180);
        this.playerNumber = playerNumber;
        players = new Player[playerNumber];
        populateBoard();
        createPlayer(playerNumber);
        for (var player : players) {
            System.out.println(player);
        }
        System.out.println("---------------------------------");
    }

    /**
     * @author : ZiHan Yuan
     * Generate a new Marrakech game with specific playerNumber
     *
     * @param realPlayerNum the player as human player
     * @param comPlayerNum  the player as AI
     */
    public Marrakech(int realPlayerNum, int comPlayerNum) {
        assam = new Assam(new IntPair(DEFAULT_ASSAM_X, DEFAULT_ASSAM_Y), Angle.DEG_180);
        this.playerNumber = realPlayerNum + comPlayerNum;
        players = new Player[playerNumber];
        populateBoard();
        createPlayerHasCom(playerNumber, comPlayerNum);
        for (var player : players) {
            System.out.println(player);
        }
        System.out.println("---------------------------------");
    }

    /**
     * @author : Team
     * Generate a new Marrakech game with input String
     *
     * @param gameState the input String stand for a specific game state.
     */
    public Marrakech(String gameState) {
        this.playerNumber = (int) gameState.codePoints().filter(ch -> ch == 'P').count();
        players = new Player[playerNumber];
        populateBoard(gameState);
    }

    /**
     * @author : ZiHan Yuan
     * a public method to get the current player
     *
     * @return the Player Object that is processioning the game
     */
    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    /**
     * @author : ChangLai Sun
     * To initialize the game state at first
     */
    public void populateBoard() {
        // creating empty tiles
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                boardMatrix[x][y] = new Tile();
            }
        }
    }

    /**
     * @author : JinYang Zeng
     * To initialize the game state with a saved game state
     * @param gameState the String that stands for a certain game state
     */
    public void populateBoard(String gameState) {
        constructGameFromGameState(gameState);
    }

    /**
     * @author : JinYang Zeng
     * modify a game state to a given game state that presented as String
     * @param gameState the game state presented as String
     */
    public void constructGameFromGameState(String gameState) {
        // populate the state of players
        createPlayer(gameState);

        // populate the state of Assam
        String assamString = getSubStringFromGameString(gameState, 1);
        this.assam = new Assam(assamString);

        // populate the state of Board
        String rugString = getSubStringFromGameString(gameState, 2);
        getBoardMatrixFromRugString(rugString);

    }

    /**
     * @author : JinYang Zeng
     * modify a BoardMatrix to a given game state that presented as String
     * @param rugString sub string of the game state that from the word b
     */
    private void getBoardMatrixFromRugString(String rugString) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                boardMatrix[x][y] = new Tile();
                String rug = rugString.substring((x * BOARD_HEIGHT + y) * 3, (x * BOARD_HEIGHT + y) * 3 + 3) + x + y + x + y;
                Rug rugTemp = new Rug(rug);
                boardMatrix[x][y].addPlacedRugs(rugTemp);
                rugs.add(rugTemp);
            }
        }
    }

    /**
     * @author : ChangLai Sun
     * method that used to initialize the players
     * @param playerNumber the number that this game contains
     */
    public void createPlayer(int playerNumber) {
        for (int i = 0; i < playerNumber; i++) {
            players[i] = new Player(Player.getRandomColor());
        }
    }

    /**
     * @author : ZiHan Yuan
     * used to initialize game with AI players
     * @param playerNumber number of HumanPlayer
     * @param comPlayerNum number of AI players
     */
    public void createPlayerHasCom(int playerNumber, int comPlayerNum) {
        int j = playerNumber - comPlayerNum;
        for (int i = 0; i < j; i++) {
            players[i] = new Player(Player.getRandomColor());
        }
        for (int i = j; i < playerNumber; i++) {
            players[i] = new Player(Player.getRandomColor());
            players[i].setCom();
        }
    }

    /**
     * @author : ChangLai Sun
     * used to create game with certain game State
     * @param gameState game state that represent by String.
     */
    public void createPlayer(String gameState) {
        int endOfPlayerString = gameState.indexOf("A");
        String playerString = gameState.substring(0, endOfPlayerString);
        String[] playerChunks = playerString.split("(?<=\\G........)");
        for (int i = 0; i < playerChunks.length; i++) {
            this.players[i] = new Player(playerChunks[i]);
        }
    }

    /**
     * @author ZiHan Yuan
     * used to get Player from color
     * @param color the color stand for the player
     * @return Player Object using the color
     */
    public Player getPlayerFromColor(Color color) {
        for (Player player : players) {
            if (player.getColor() == color) return player;
        }
        return null;
    }

    /**
     * @author JinYang Zeng
     * Override the toString method.
     *
     * @return gameString
     */
    @Override
    public String toString() {
        StringBuilder gameState = new StringBuilder();
        for (Player p : players) {
            gameState.append(p);
        }
        gameState.append(assam);
        gameState.append("B");
        for (int i = 0; i < BOARD_HEIGHT; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                gameState.append(this.getTileFromPos(new IntPair(i, j)).getTopRug().toString(), 0, 3);
            }

        }
        return gameState.toString();
    }

    /**
     * @author ChangLai Sun
     * Helper method to fetch a particular tile given by a position represented as
     * an IntPair
     *
     * @param position in the board matrix (see readme for explanation)
     * @return Tile at that position if on the board, null otherwise
     */
    public Tile getTileFromPos(IntPair position) {
        if (withinBoard(position)) {
            return this.boardMatrix[position.getX()][position.getY()];
        } else {
            return null;
        }
    }

    /**
     * @author ChangLai Sun
     * Checks if a position represented as an IntPair is on the board
     *
     * @param position to check
     * @return true if it is on the board
     */
    public static boolean withinBoard(IntPair position) {
        // 0<= X < 5, 0 <= Y < 5
        if (position.getX() >= BOARD_WIDTH || position.getX() < 0) {
            return false;
        }
        return position.getY() < BOARD_HEIGHT && position.getY() >= 0;
    }

    /**
     * @author ChangLai Sun
     * apply rotation on assam
     * @param transform a specific rotation defined by degree
     */
    public void rotateAssam(Transform transform) {
        assam.applyTransform(transform);
    }


    /**
     * @author Jinyang Zeng
     * Determine whether a rug String is valid.
     * - The String is 7 characters long
     * - The first character in the String corresponds to the colour character of a
     * player present in the game
     * - The next two characters represent a 2-digit ID number
     * - The next 4 characters represent coordinates that are on the board
     * - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board,
     * but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the
     * same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then
     * we can have the following rugs
     * - c023343 (Shares the colour but not the ID)
     * - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug
     * on the board already.
     *
     * @param gameState A String representing the current state of the game as per
     *                  the README
     * @param rugString A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameState, String rugString) {
        //to make this method can be used in our real game, made it OOP
        Marrakech marrakech = new Marrakech(gameState);
        //check the color is valid or not
        if (!isColorValid(rugString)) {
            return false;
        }
        //new the rug
        Rug rug = new Rug(rugString);
        //check the validation in the instance method.
        return marrakech.isRugValid(rug);

        // FIXME: Finished Task 4
    }


    /**
     * @author Jinyang Zeng
     * the real method that define a rug valid or not
     * @param rug the rug to be checked
     * @return true if is valid
     */
    public boolean isRugValid(Rug rug) {

        // check if the rug run outside the matrix
        IntPair[] rugs = rug.getRelativeSegmentPositions();
        for (IntPair i : rugs) {
            if (!withinBoard(i)) {
                return false;
            }
        }

        // check the rugId available for the gameState
        // call the rug instance method to compare color and id.
        for (Rug usedRug : this.rugs) {
            if (!usedRug.isRugValid(rug)) {
                return false;
            }

        }

        // check the rug not divided
        return rugs[0].isAdjacentTo(rugs[1]);
    }

    /**
     * @author Jinyang Zeng
     * check the color in rugString valid or not
     * @param rugString the input to be checked whether it can be translated into a rug Object
     * @return true if color can represent a player
     */
    private static boolean isColorValid(String rugString) {
        char colorLetter = rugString.charAt(0);
        for (Color color : Color.values()) {
            if (colorLetter != 'n' && colorLetter == color.value) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author Jinyang Zeng
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     * - One shows 1
     * - Two show 2
     * - Two show 3
     * - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or
     * 3
     * being twice as likely to be returned as a 1 or 4.
     *
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        //to make this method can be used in our real game, made it OOP
        // FIXME: Finished Task 6
        return gameDie.roll();
    }

    /**
     * @author Jinyang Zeng
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about
     * to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game
     * state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine
     * whether a game is over or not.
     *
     * @param gameState A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String gameState) {
        // FIXME: Finished Task 8
        //to make this method can be used in our real game, made it OOP
        Marrakech marrakech = new Marrakech(gameState);
        return marrakech.isGameOver();
    }

    /**
     * @author ChangLai Sun
     * Check if the game reach the final state
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        boolean flag = true;
        for (Player player : players) {
            //if there is still any player that in game and have rug the game is not over.
            if (player.isInGame() && player.getRugsRemaining() != 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * @author Jinyang Zeng
     * Check if the game reach the final state,the method above only works on String state
     * @return if the game over return true.
     */
    public boolean isGame_Over() {
        int count = 0;
        for (Player player : players) {
            //All the player is failed player or reached the final state that run out of their rugs.
            if (!player.isInGame() || player.getRugsRemaining() == 0) {
                count += 1;
            }
        }
        return count == playerNumber;
    }

    /**
     * @author Jinyang Zeng
     * Check if the game over at the point when someone bankrupted.
     * @return true if other players bankrupted.
     */
    public boolean isGame_Over_OtherPlayersBroken() {
        int count = 0;
        for (Player player : players) {
            if (!player.isInGame()) {
                count += 1;
            }
        }
        // count the number of bankrupted if there is only one player left game over.
        return count == playerNumber - 1;
    }

    /**
     * @author ZiHan Yuan
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he
     * cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board),
     * then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree
     * increments.
     * If the requested rotation is illegal, you should return Assam's current state
     * unchanged.
     *
     * @param currentAssam A String representing Assam's current state
     * @param rotation     The requested rotation, in degrees. This degree reading
     *                     is relative to the direction Assam
     *                     is currently facing, so a value of 0 for this argument
     *                     will keep Assam facing in his
     *                     current orientation, 90 would be turning him to the
     *                     right, etc.
     * @return A String representing Assam's state after the rotation, or the input
     * currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // FIXME: Task 9
        Marrakech marrakech = new Marrakech();
        marrakech.assam = new Assam(currentAssam);
        Transform newTransform = new Transform(rotation);
        if (newTransform.getRotation() != null) {
            marrakech.rotateAssam(newTransform);
        }
        return marrakech.assam.toString();
    }

    /**
     * @author Jinyang Zeng
     * Determine whether a potential new placement is valid (i.e. that it describes
     * a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which
     * are detailed in the README but to
     * reiterate here:
     * 1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     * 2. A new rug must not completely cover another rug. It is legal to partially
     * cover an already placed rug, but
     * the new rug must not cover the entirety of another rug that's already on the
     * board.
     *
     * @param gameState A game string representing the current state of the game
     * @param rugString A rug string representing the candidate rug which you must
     *                  check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rugString) {
        //to make this method can be used in our real game, made it OOP
        Marrakech marrakech = new Marrakech(gameState);
        Rug rug = new Rug(rugString);
        // FIXME: Finished Task 10
        return marrakech.isPlacementValid(rug);
    }

    /**
     * @author Jinyang Zeng
     * @param rug rug object need to be checked
     * @return true if the placement adjacent to assam and not cover another rug in both tiles.
     */
    public boolean isPlacementValid(Rug rug) {
        if (!isRugValid(rug)) {
            return false;
        }

        boolean condition1 = isNewRugAdjacentToAssam(rug);
        boolean condition2 = !isNewRugCompletelyCoverAnotherRug(rug);
        return condition1 && condition2;
    }

    /**
     * @author Jinyang Zeng
     * @param rug rug object need to be checked
     * @return true if the placement is adjacent to Assam.
     */
    private boolean isNewRugAdjacentToAssam(Rug rug) {
        IntPair assamPosition = assam.getPosition();
        IntPair[] intPairs = rug.getRelativeSegmentPositions();

        //the rug cannot cover assam
        for (IntPair i : intPairs) {
            if (i.equals(assamPosition)) {
                return false;
            }
        }
        //any one rugSegment adjacent to assam the result is true
        for (IntPair i : intPairs) {
            if (i.isAdjacentTo(assamPosition)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @author Jinyang Zeng
     *
     * @param rug rug Object need to be checked
     * @return true if the rug cover the existing rug completely
     */

    private boolean isNewRugCompletelyCoverAnotherRug(Rug rug) {

        IntPair[] intPairs = rug.getRelativeSegmentPositions();


        Tile tileA = this.getTileFromPos(intPairs[0]);
        Tile tileB = this.getTileFromPos(intPairs[1]);
        if (tileA == null || tileB == null) {
            return true;
        }
        Rug rugA = tileA.getTopRug();
        Rug rugB = tileB.getTopRug();


        if (rugA != null && rugB != null) {
            //if the first rug cover an empty tile
            if (rugA.getColor() == Color.NOCOLOR) return false;
            String RugA = "" + tileA.getTopRug().getColor().value + tileA.getTopRug().getId();
            String RugB = "" + tileB.getTopRug().getColor().value + tileB.getTopRug().getId();
            //check the two rug color and id are same or not
            return RugA.equals(RugB);
        }
        return false;
    }

    /**
     * @author Jinyang Zeng
     * method to get subString from game state
     *
     * @param gameState       A game string representing the current state of the
     *                        game
     * @param targetSubString get the player Strings with 0 ,get Assam String with
     *                        1,
     *                        get RugStrings with 2,get Board String with 3.
     * @return the substring you want
     */
    static String getSubStringFromGameString(String gameState, int targetSubString) {
        // targetSubString 0 for Player,1 for Assam,2 for rugs ,others for board string
        int indexOfBoardState = gameState.indexOf('B');
        String output;
        if (targetSubString == 0) {
            output = gameState.substring(0, indexOfBoardState - 4);
        } else if (targetSubString == 1) {
            output = gameState.substring(indexOfBoardState - 4, indexOfBoardState);
        } else if (targetSubString == 2) {
            output = gameState.substring(indexOfBoardState + 1);
        } else {
            output = gameState.substring(indexOfBoardState);
        }
        return output;
    }

    /**
     * @author Jinyang Zeng
     * Determine the amount of payment required should another player land on a
     * square.
     * For this method, you may assume that Assam has just landed on the square he
     * is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on
     * (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the
     * number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two
     * squares are only connected if they
     * share an entire edge -- diagonals do not count.
     *
     * @param gameState A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */

    public static int getPaymentAmount(String gameState) {
        //to make this method can be used in our real game, made it OOP
        Marrakech marrakech = new Marrakech(gameState);
        return marrakech.getPaymentAmount(0);
    }

    /**
     * @author Jinyang Zeng
     * @param i when use this method in an instance that constructed from string the current player can be initialized
     *          so with this parameter pass the condition. 0 stands for test 1 stands for real game.
     * @return the money current player need to pay.
     */

    public int getPaymentAmount(int i) {
        int positionX = assam.getPosition().getX();
        int positionY = assam.getPosition().getY();

        Tile assamLocation = getTileFromPos(assam.getPosition());
        assert assamLocation != null;
        //get the color of tile currentAssam position
        Color color = assamLocation.getTopRug() == null ? Color.NOCOLOR : assamLocation.getTopRug().getColor();
        if (color == Color.NOCOLOR) {
            return 0;
        }
        //failed player no need to pay
        if (getPlayerFromColor(color) == null) {
            return 0;
        }
        //tile belong to the player-self
        if (i == 1) {
            if (players[this.currentPlayerIndex].getColor() == color) {
                return 0;
            }
        }
        boolean[][] visited = new boolean[BOARD_WIDTH][BOARD_WIDTH];
        return countConnectedBlocks(color.value, boardMatrix, positionX, positionY, visited);
    }

    /**
     * @author Jinyang Zeng
     * recursive method to calculate the connectedtiles with same color
     * @param color the color u want to check
     * @param boardMatrix the board state
     * @param x the position of column
     * @param y the position of row
     * @param visited a boolean[][] used to target if the tile has been checked
     * @return the total number of connected blocks
     */
    private static int countConnectedBlocks(char color, Tile[][] boardMatrix, int x, int y, boolean[][] visited) {
        //if out the board or have checked or Empty
        if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT || visited[x][y] || boardMatrix[x][y].getTopRug() == null || boardMatrix[x][y].getTopRug().getColor().value != color)
            return 0;
        visited[x][y] = true;
        return 1 + countConnectedBlocks(color, boardMatrix, x - 1, y, visited)
                + countConnectedBlocks(color, boardMatrix, x + 1, y, visited)
                + countConnectedBlocks(color, boardMatrix, x, y - 1, visited)
                + countConnectedBlocks(color, boardMatrix, x, y + 1, visited);
    }


    /**
     * @author Jinyang Zeng
     * used to define which direction is best to avoid paying
     * @param difficulty stands for AI 's difficulty
     *                   if easy or medium will rotate assam randomly
     *                   if hard assam will go to the direction that pay as little as possible,if multiple direction available
     *                   randomly select one.
     */
    public void bestDirection(int difficulty) {

        assam.applyTransform(new Transform(270));
        //for easy and normal AI
        if (difficulty == 0 || difficulty == 1) {
            for (int i = 0; i < gameDie.random.nextInt(3); i++) {
                assam.applyTransform(new Transform(90));
            }
            return;
        }
        int[] sums = new int[3];
        // get the min payment in 3 directions
        int min = evaluateDirection(sums);

        // get the indexOfDirection that can pay little
        ArrayList<Integer> possibleIndexOfDirection = new ArrayList<>();
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] == min) {
                possibleIndexOfDirection.add(i);
            }
        }
        // select one of the best direction
        int best = gameDie.random.nextInt(possibleIndexOfDirection.size());

        //rotate Assam
        switch (possibleIndexOfDirection.get(best)) {
            case 0 -> {
                assam.applyTransform(new Transform(270));
                System.out.println("AI choose to turn left");
                System.out.println("---------------------------------");
            }
            case 1 -> {
                System.out.println("AI choose to go straight");
                System.out.println("----------------------------");
            }
            case 2 -> {
                assam.applyTransform(new Transform(90));
                System.out.println("AI choose to turn right");
                System.out.println("----------------------------");
            }
        }
    }

    /**
     * @author Jinyang Zeng
     * test 3 directions and evaluate
     * @param sums index of directions
     * @return minimum payment
     */
    public int evaluateDirection(int[] sums) {
        //test 3 directions
        for (int i = 0; i < 3; i++) {
            String assamState = assam.toString();
            for (int j = 1; j < 5; j++) {
                assam.preMove(j);
                if (j == 1 || j == 4) {
                    sums[i] += getPaymentAmount(1);
                } else {
                    sums[i] += getPaymentAmount(1) * 2;
                }
                assam = new Assam(assamState);
            }
            assam.applyTransform(new Transform(90));
        }
        assam.applyTransform(new Transform(90));
        assam.applyTransform(new Transform(90));
        int min = Arrays.stream(sums).min().getAsInt();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        System.out.println("最小值" + decimalFormat.format((double)min/6));
        System.out.print("Turn left will pay " + decimalFormat.format((double) sums[0] / 6) + "\t");
        System.out.print("Go straight will pay " + decimalFormat.format((double) sums[1] / 6) + "\t");
        System.out.println("Turn right will pay " + decimalFormat.format((double) sums[2] / 6));
        System.out.println("---------------------------------");
        return min;
    }


    //used to help human player
    public void bestDirectionForHuman() {
        assam.applyTransform(new Transform(270));
        int[] sums = new int[3];
        evaluateDirection(sums);

    }

    /**
     * @author Jinyang Zeng
     * get all the possible rug can place
     * @return ArrayList<Rug> all can be placed
     */
    private ArrayList<Rug> possibleRugPlacement() {

        IntPair position = assam.getPosition();
        ArrayList<Rug> possibleRugPlacement = new ArrayList<>();

        //extend rug for 4 direction
        ArrayList<IntPair> directions = make4DirectionIntPair();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i + 2) % 4 != j) {
                    // cant do up and down it's same tile
                    Rug temp = new Rug(getCurrentPlayer().getColor(), position.add(directions.get(i)), position.add(directions.get(i)).add(directions.get(j)), 1);
                    if (isPlacementValid(temp)) {
                        possibleRugPlacement.add(temp);
                    }
                }
            }
        }
        return possibleRugPlacement;
    }

    /**
     * @author Jinyang Zeng
     * @param difficulty stands for AI 's difficulty
     *               if easy will place rug randomly
     *                if medium or hard place rug best.
     * @return the position of best placed rug
     */
    public IntPair[] bestPlacement(int difficulty) {
        ArrayList<Rug> bestRugs = new ArrayList<>();
        HashMap<Rug, Integer> rugsAndScore = new HashMap<>();
        ArrayList<Rug> possibleRugPlacement = possibleRugPlacement();

        //random select one possible rug to place
        if (difficulty == 0) {
            int indexOfRandomRug = gameDie.random.nextInt(possibleRugPlacement.size());
            IntPair[] randomRugIntpair = possibleRugPlacement.get(indexOfRandomRug).getRelativeSegmentPositions();
            Arrays.sort(randomRugIntpair, new IntPairComparator());
            return randomRugIntpair;
        }

        //dictionary for every possible rug and its score
        for (Rug rug : possibleRugPlacement) {
            rugsAndScore.put(rug, placeScore(getCurrentPlayer(), rug));
        }

        int maxScoreForPlacement = rugsAndScore.values().stream().max(Comparator.naturalOrder()).orElse(0);

        //get all the best placement with the highest score
        for (Map.Entry<Rug, Integer> entry : rugsAndScore.entrySet()) {
            if (entry.getValue() == maxScoreForPlacement) {
                bestRugs.add(entry.getKey());
            }
        }
        System.out.print("best Placement");
        for (Rug r : bestRugs) {
            System.out.print(" " + r + " with score " + placeScore(getCurrentPlayer(), r) + " ");
        }
        System.out.println();
        IntPair[] bestIntPair;
        //randomly select one rug in best and return its position
        bestIntPair = bestRugs.get(gameDie.random.nextInt(bestRugs.size())).getRelativeSegmentPositions();

        Arrays.sort(bestIntPair, new IntPairComparator());

        return bestIntPair;
    }


    private final int ADJACENT_SCORE = 10;
    private final int COVER_HIGHEST_SCORE_PLAYER = 5;
    private final int COVER_OTHER_PLAYER = 2;
    private final int COVER_EMPTY_TILE = 0;
    private final int SELF_COVERED = -2;

    /**
     * @author Jinyang Zeng
     * Rules for evaluate a placement used for enhanced AI
     * if the placement can make rugs adjacent + 10
     * if the placement can cover a highest-score player  each + 5
     * if the placement can cover other player each + 2
     * if the placement cover the empty tile OR cover the failed player each + 0
     * if the placement self-covered each -2
     * if the placement self-covered both, the placement would be passed
     * only the player still in game would be considered
     *
     * @param player current player
     * @param rug    the tempRug to be evaluated (not yey placed)
     * @return the score of the placement evaluated by the rules
     */
    private int placeScore(Player player, Rug rug) {
        int sums = 0;
        int minusCount = 0;
        //Apply the adjacent rules to get score.
        if (isAdjacentToSameColor(rug)) sums += ADJACENT_SCORE;

        ArrayList<Player> playersInGame = new ArrayList<>();
        for (Player p : players) {
            if (p.isInGame()) {
                playersInGame.add(p);
            }
        }
        //only need the inGamePlayer except the player self when counting the highest score.
        playersInGame.remove(player);
        ArrayList<Color> colorsOfPlayersInGame = new ArrayList<>();
        for (Player p : playersInGame) {
            colorsOfPlayersInGame.add(p.getColor());
        }

        ArrayList<Player> playersWithHighScoreExceptPlayerSelf = getPlayersWithHighScore(playersInGame);
        ArrayList<Color> colorsOfHighScorePlayers = new ArrayList<>();
        for (Player p : playersWithHighScoreExceptPlayerSelf) {
            colorsOfHighScorePlayers.add(p.getColor());
        }

        //Apply the covering rules to get score.
        for (IntPair i : rug.getRelativeSegmentPositions()) {
            Tile tile = getTileFromPos(i);
            if (tile.getTopRug() == null) {
                sums += COVER_EMPTY_TILE;
            } else {
                Color color = tile.getTopRug().getColor();
                if (color == player.getColor()) {
                    sums += SELF_COVERED;
                    minusCount += 1;
                } else if (colorsOfHighScorePlayers.contains(color)) {
                    sums += COVER_HIGHEST_SCORE_PLAYER;
                } else if (colorsOfPlayersInGame.contains(color)) {
                    sums += COVER_OTHER_PLAYER;
                }
            }
        }
        // avoid the self-cover both  for this can get 3 points at least better than some specific situation
        if (minusCount == 2) {
            return -1;
        }
        return sums;
    }

    /**
     * @author JinYang Zeng
     * a helper method to get player(s) with high score
     * @param players the player that still in game
     * @return an arraylist of player(s)
     */
    private ArrayList<Player> getPlayersWithHighScore(ArrayList<Player> players) {
        ArrayList<Player> highScorePlayers = new ArrayList<>();
        int maxScoreExceptPlayerSelf = -1;
        for (Player p : players) {
            if (p.getScore() > maxScoreExceptPlayerSelf) {
                maxScoreExceptPlayerSelf = p.getScore();
            }
        }

        for (Player p : players) {
            if (p.getScore() == maxScoreExceptPlayerSelf) {
                highScorePlayers.add(p);
            }
        }
        return highScorePlayers;
    }

    /**
     * @author JinYang Zeng
     * a helper method to check the place to put is adjacent to the currentplayer's color
     * @param rug the Rug going to be placed
     * @return true for can do adjacent
     */
    private boolean isAdjacentToSameColor(Rug rug) {

        ArrayList<IntPair> directions = make4DirectionIntPair();

        ArrayList<IntPair> toBeTest = new ArrayList<>();
        for (int i = 0; i < rug.getRelativeSegmentPositions().length; i++) {
            for (IntPair direction : directions) {
                toBeTest.add(rug.getRelativeSegmentPositions()[i].add(direction));
            }
        }

        for (IntPair position : toBeTest) {
            if (getTileFromPos(position) != null) {
                if (getTileFromPos(position).getTopRug() != null) {
                    if (rug.getColor() == getTileFromPos(position).getTopRug().getColor()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private ArrayList<IntPair> make4DirectionIntPair() {
        ArrayList<IntPair> directions = new ArrayList<>();
        directions.add(new IntPair(0, -1));
        directions.add(new IntPair(1, 0));
        directions.add(new IntPair(0, 1));
        directions.add(new IntPair(-1, 0));
        return directions;
    }




    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to
     * return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner,
     * then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and
     * the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game
     * cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If
     * multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     *
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        Marrakech marrakech = new Marrakech(gameState);
        if (!marrakech.isGameOver()) {
            return 'n';
        }
        // FIXME: Finished Task 12
        return marrakech.getWinner();
    }

    public void renewPlayersScore() {
        for (Player p : players) {
            int score = getScore(p);
            p.setScore(score);
        }
    }

    public char getWinner() {

        int maxScore = getHighScore();

        // find the winner of game

        ArrayList<Player> playersToWin = new ArrayList<>();
        for (Player p : players) {
            if (getScore(p) == maxScore) {
                playersToWin.add(p);
            }
        }


        if (playersToWin.size() == 1) {
            return playersToWin.get(0).getColor().value;
        }

        ArrayList<Player> lastWinner = new ArrayList<>();
        int maxCoin = -1;
        for (Player p : playersToWin) {
            if (p.getDirhams() > maxCoin) {
                maxCoin = p.getDirhams();
            }
        }
        for (Player p : playersToWin) {
            if (p.getDirhams() == maxCoin) {
                lastWinner.add(p);
            }
        }

        if (lastWinner.size() == 1) {
            return lastWinner.get(0).getColor().value;
        }

        return 't';
    }

    private int getScore(Player player) {
        int coins = player.getDirhams();
        char color = player.getColor().value;

        int rugCount = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                if (boardMatrix[i][j].getTopRug() == null) {
                    continue;
                }
                if (boardMatrix[i][j].getTopRug().getColor().value == color) {
                    rugCount++;
                }
            }

        }

        return coins + rugCount;
    }


    private int getHighScore() {
        // find the max score

        int maxScore = -1;
        for (int i = 0; i < playerNumber; i++) {
            Player currentPlayer = players[i];
            if (currentPlayer.isInGame()) {
                int currentPlayerScore = getScore(currentPlayer);
                if (currentPlayerScore > maxScore) {
                    maxScore = currentPlayerScore;
                }
            }
        }
        return maxScore;
    }

    public void takeTurn() {
        do {
            this.currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
//            System.out.println(players[currentPlayerIndex].getColor().value + " is available");
        } while (!players[currentPlayerIndex].isInGame());

    }


    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by
     * the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results
     * in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be
     * studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the
     * die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of
     * these are valid.
     *
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult    The result of the die, which determines the number of
     *                     squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult) {
        Assam assam = new Assam(currentAssam);
//        assam.move(dieResult);
        // FIXME: Task 13
        assam.preMove(dieResult);
        return assam.toString();
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and
     * moved, i.e. in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the
     * isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the
     * board after the placement has
     * been completed. If the placement is invalid, then you should return the
     * existing game unchanged.
     *
     * @param gameState A String representation of the current state of the game.
     * @param rugString A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful
     * placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String gameState, String rugString) {
        Marrakech marrakech = new Marrakech(gameState);
        if (isRugValid(gameState, rugString)) {
            Rug rug = new Rug(rugString);
            marrakech.makePlacement(rug);
        }
        // FIXME: Task 14
        return marrakech.toString();
    }

    private void makePlacement(Rug rug) {
        if (this.isPlacementValid(rug)) {
            this.getTileFromPos(rug.getRelativeSegmentPositions()[0]).addPlacedRugs(rug);
            this.getTileFromPos(rug.getRelativeSegmentPositions()[1]).addPlacedRugs(rug);
            for (Player p : players) {
                if (rug.getColor() == p.getColor()) {
                    p.reduceRugsRemaining();
                }
            }
        }
    }
}
