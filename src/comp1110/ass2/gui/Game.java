package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static javafx.scene.paint.Color.*;

public class Game extends Application {
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final double Tile_Size = 70; //The size of small block on board
    private static final double BoardStart_X = 162; // The start of the board in the x-direction
    private static final double BoardStart_Y = 88; // The start of the board in the y-direction
    private static final double Board_PicStart_X = 80; // The start of the board picture in the x-direction
    private static final double Board_PicStart_Y = 20; // The start of the board picture in the y-direction
    private static final double Board_Pic_Width = 662; //The width of Board Picture
    private static final double Board_Pic_Height = 644; //The height of Board Picture
    private static final double PlayerStart_X = 820; //The start of PlayerBoard in the x-direction
    private static final double PlayerStart_Y = 50; //The start of PlayerBoard in the y-direction
    private static final double ArrowStart_X = PlayerStart_X - 20;
    private static final double ArrowStart_Y = PlayerStart_Y + 20;
    private static final double DiceStart_Y = 454;
    private static final double RestartButtonStart_X = 1040;
    private final Group diceBoard = new Group();
    private Marrakech marrakech;
    private int realPlayerNum = 4;
    private int comPlayerNum = 0;
    private int playerNum = realPlayerNum + comPlayerNum;
    private int difficulty = 0;
    MediaPlayer mediaPlayer;
    Pane paneAssam = new Pane();
    VBox playervbox = new VBox();//a vbox of player information
    Text information = new Text();//show game information
    Button restartButton = new Button("Restart");

    private void newGame() {
        makeBackground();
        makeBoard();
        this.marrakech = new Marrakech(realPlayerNum, comPlayerNum);
        root.getChildren().add(restartButton);
        restartButton.setLayoutX(RestartButtonStart_X);
        restartButton.setLayoutY(PlayerStart_Y + 30);
        restartButton.setOnAction(event -> {
            stopAITurnInFirst = true;
            this.restart();
        });

        displayPlayer();
        displayArrow();
        displayAssam();
        displayInformationBoard();
        music();
        PhaseOne();
    }

    private void displayInformationBoard() {
        information.setFill(BLACK);
        information.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 16));
        information.setWrappingWidth(320);
        information.setTextAlignment(TextAlignment.CENTER);

        Rectangle infoRec = new Rectangle(320, 35);
        infoRec.setFill(WHITE);
        infoRec.setOpacity(0.5);

        Rectangle messageRec = new Rectangle(80, 20);
        messageRec.setFill(GREY);
        messageRec.setOpacity(0.5);
        messageRec.setLayoutX(PlayerStart_X);
        Text message = new Text("Message");

        message.setFill(WHITE);
        message.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
        message.setWrappingWidth(80);
        message.setTextAlignment(TextAlignment.CENTER);

        StackPane infoPane = new StackPane();
        infoPane.setLayoutX(PlayerStart_X);
        infoPane.setLayoutY(BoardStart_Y + Board_Pic_Height - 145);
        infoPane.getChildren().addAll(infoRec, information);

        StackPane messagePane = new StackPane();
        messagePane.setLayoutX(PlayerStart_X);
        messagePane.setLayoutY(BoardStart_Y + Board_Pic_Height - 165);
        messagePane.getChildren().addAll(messageRec, message);
        messagePane.setAlignment(Pos.TOP_LEFT);
        root.getChildren().addAll(infoPane, messagePane);
    }

    private void displayArrow() {
        // an arrow to indicate which player is playing
        Polygon arrow = new Polygon(0, 20, -20, 0, -20, 15, -40, 10, -40, 30, -20, 25, -20, 40);
        arrow.setFill(Color.RED);
        arrow.setOpacity(0.7);
        arrow.setLayoutX(ArrowStart_X);
        arrow.setLayoutY(ArrowStart_Y);
        root.getChildren().add(arrow);
    }

    private void makeBackground() {
        Image backImage = new Image("file:src/comp1110/ass2/gui/assets/Dessertwithassam.png");
        ImageView imageView = new ImageView();
        imageView.setImage(backImage);
        imageView.setOpacity(0.7);
        imageView.setFitWidth(WINDOW_WIDTH);
        imageView.setFitHeight(WINDOW_HEIGHT);
        root.getChildren().remove(imageView);
        root.getChildren().add(imageView);
    }

    /**
     * To make board, add board image
     */
    private void makeBoard() {
        Image boardImage = new Image(Objects.requireNonNull(Viewer.class.getResource("assets/Board Image.png")).toString());
        ImageView BoardimageView = new ImageView();
        BoardimageView.setImage(boardImage);
        BoardimageView.setOpacity(0.9);
        BoardimageView.setFitHeight(Board_Pic_Height);
        BoardimageView.setFitWidth(Board_Pic_Width);
        BoardimageView.setLayoutX(Board_PicStart_X);
        BoardimageView.setLayoutY(Board_PicStart_Y);
        root.getChildren().remove(BoardimageView);
        root.getChildren().add(BoardimageView);
    }

    private void displayPlayer() {
        playervbox.setSpacing(15);
        for (int i = 0; i < playerNum; ++i) {
            playervbox.getChildren().add(this.createPlayerPane(marrakech.players[i]));
        }
        playervbox.setLayoutX(PlayerStart_X);
        playervbox.setLayoutY(PlayerStart_Y);
        root.getChildren().remove(playervbox);
        root.getChildren().add(playervbox);
    }

    /**
     * @param player the current player
     * @return A pane of param player to show information
     */
    private Pane createPlayerPane(Player player) {
        Pane pane = new Pane();
        Rectangle upperRectangle = new Rectangle(0, 0, 160, 20);
        int playerId = player.getPlayId();
        String playerNumber = "            Player " + (playerId + 1);
        if (player.isComputer()) playerNumber += " (Com)";
        upperRectangle.setFill(player.getColor().getFillColor());
        upperRectangle.setOpacity(0.9);
        Rectangle whiteRectangle = new Rectangle(0, 20, 160, 60);
        whiteRectangle.setFill(Color.WHITE);
        whiteRectangle.setOpacity(0.9);

        GridPane playerGridPane = getGridPane(playerId, playerNumber);

        pane.getChildren().addAll(upperRectangle, whiteRectangle, playerGridPane);
        return pane;
    }

    private GridPane getGridPane(int playerId, String playerNumber) {
        GridPane playerGridPane = new GridPane();
        playerGridPane.setLayoutX(0);
        playerGridPane.setLayoutY(0);
        playerGridPane.setVgap(2);
        playerGridPane.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-alignment: center;");

        String dirhams = "  Dirhams: " + marrakech.players[playerId].getDirhams();
        String remainingRugs = "  Remaining Rugs: " + marrakech.players[playerId].getRugsRemaining();
        String score = "  Score: " + marrakech.players[playerId].getScore();

        Label playerLabel = new Label(playerNumber);
        playerLabel.setStyle("-fx-text-fill: white;");
        Label dirhamsLabel = new Label(dirhams);
        Label remainingRugsLabel = new Label(remainingRugs);
        Label scoreLabel = new Label(score);

        playerGridPane.add(playerLabel, 0, 0);
        playerGridPane.add(dirhamsLabel, 0, 1);
        playerGridPane.add(remainingRugsLabel, 0, 2);
        playerGridPane.add(scoreLabel, 0, 3);
        return playerGridPane;
    }

    private int newRotation = 0;// to count mouseScroll Angle

    /**
     * @param orientation the direction assam faced to
     * @return get Assam picture path from current Assam orientation
     */
    private String AssamPath(char orientation) {
        return "assets/Assam" + orientation + ".png";
    }

    private void displayAssam() {
        IntPair intPair = marrakech.assam.getPosition();
        int assamX = intPair.getY();
        int assamY = intPair.getX();
        char orientation = marrakech.assam.getOrientation();
        Image imageAssam = new Image(Objects.requireNonNull(Viewer.class.getResource(AssamPath(orientation))).toString());
        ImageView AssamImageView = new ImageView();
        AssamImageView.setImage(imageAssam);
        AssamImageView.setFitHeight(Tile_Size);
        AssamImageView.setFitWidth(Tile_Size);
        paneAssam.getChildren().add(AssamImageView);

        AssamImageView.setLayoutX(BoardStart_X + assamY * Tile_Size);
        AssamImageView.setLayoutY(BoardStart_Y + assamX * Tile_Size);
        paneAssam.getChildren().clear();
        paneAssam.getChildren().add(AssamImageView);
        root.getChildren().remove(paneAssam);
        root.getChildren().add(paneAssam);
    }

    /**
     * @param number current Dice number
     * @return get Dice picture path from current Dice number
     */
    private String DicePicPath(int number) {
        return "assets/Dice" + number + ".png";
    }

    /**
     * @param c current rug color
     * @return Rug picture path from current rug color
     */
    private String RugPath(char c) {
        return "assets/Rug" + c + ".png";
    }

    private void displayDice(int DieNum) {
        //add Dice image
        Image diceImg = new Image(Objects.requireNonNull(Objects.requireNonNull(Viewer.class.getResource(DicePicPath(DieNum))).toString()));
        ImageView DiceImageView = new ImageView();
        DiceImageView.setImage(diceImg);
        DiceImageView.setFitWidth(Tile_Size);
        DiceImageView.setFitHeight(Tile_Size);
        DiceImageView.setLayoutX(PlayerStart_X);
        DiceImageView.setLayoutY(DiceStart_Y);
        diceBoard.getChildren().remove(DiceImageView);
        diceBoard.getChildren().add(DiceImageView);
    }

    private int DieNum;//record Die Number
    boolean allowClickRollDice = true;

    /**
     * display Roll Dice button
     */
    private void displayRollDiceControl() {
        allowDetectMouseClick = false;
        Button roDieButton = new Button("Roll Dice");
        System.out.println("Please click the button to Roll Dice");
        System.out.println("---------------------------------");
        information.setText("Please click the button to Roll Dice");
        roDieButton.setOnAction(event -> {
            if (allowClickRollDice) {
                allowDetectMouseClick = false;
                DieNum = Marrakech.rollDie();
                displayDice(DieNum);
                if (DieNum == 1) information.setText("Assam moved " + DieNum + " step");
                else information.setText("Assam moved " + DieNum + " steps");
                marrakech.assam.preMove(DieNum);
                diceBoard.getChildren().remove(roDieButton);
                PhaseTwo(marrakech.getCurrentPlayer());
            }
        });
        roDieButton.setStyle("-fx-pref-width: 70px; -fx-pref-height: 70px;");
        roDieButton.setLayoutX(PlayerStart_X);
        roDieButton.setLayoutY(DiceStart_Y);
        diceBoard.getChildren().remove(roDieButton);
        diceBoard.getChildren().add(roDieButton);
        root.getChildren().remove(diceBoard);
        root.getChildren().add(diceBoard);
    }

    boolean stopAITurnInSecond = false;

    /**
     * minus current player's number of rug remaining, and turn to next Player if game
     * is not over
     *
     * @param player the current player
     */
    public void PhaseFour(Player player) {
        if (player.isInGame()) {
            diceBoard.getChildren().clear();
            player.reduceRugsRemaining();
            playervbox.getChildren().remove(marrakech.currentPlayerIndex);
            playervbox.getChildren().add(marrakech.currentPlayerIndex, createPlayerPane(player));
        } else {
            diceBoard.getChildren().clear();
        }
        marrakech.takeTurn();//next Player
        restartButton.setVisible(true);
        for (Node child : root.getChildren()) {//change arrow position
            if (child instanceof Polygon) {
                child.setLayoutY(ArrowStart_Y + 90 * marrakech.currentPlayerIndex);
                break;
            }
        }
        Player nextPlayer = marrakech.getCurrentPlayer();
        if (nextPlayer.isComputer() && !marrakech.isGameOver()) {//after take turn the player is AI
            allowRoAssam = false;
            allowClickAssam = false;
            restartButton.setVisible(false);
            AIFirst(nextPlayer);
        } else if (marrakech.isGame_Over()) {
            stopAITurnInFirst = true;
            Platform.runLater(() -> {
                char winner = marrakech.getWinner();
                showGameOverWindow(winner);
            });
        } else {
            allowClickAssam = true;
            allowRoAssam = true;
            information.setText("Please use Scroll to Rotate Assam");
            marrakech.bestDirectionForHuman();
            System.out.println("Please use mouseScroll to Rotate Assam");
            System.out.println("---------------------------------");
        }
    }

    private void AIFourth(Player player) {
        for (Node child : root.getChildren()) {//change arrow position
            if (child instanceof Polygon) {
                child.setLayoutY(ArrowStart_Y + 90 * marrakech.currentPlayerIndex);
                break;
            }
        }
        //information.setText("Please use Scroll to Rotate Assam");
        PhaseFour(player);
    }

    boolean stopAITurnInThird = false;

    private void AIThird(Player player) {
        //5. if not broken then place rug
        Rug rug = new Rug(player.getColor());
        IntPair[] bestPos = marrakech.bestPlacement(difficulty);
        System.out.println("---------------------------------");
        System.out.println("AI placed rug at bestPos = " + bestPos[0].toString() + " " + bestPos[1].toString());
        System.out.println("---------------------------------");
        rug.setRelativeSegmentPositions(bestPos);
        marrakech.rugs.add(rug);
        int x1 = bestPos[0].getX();
        int x2 = bestPos[1].getX();
        int y1 = bestPos[0].getY();
        int y2 = bestPos[1].getY();
        Rectangle bestRec = new Rectangle(140, 70);
        Image rugImg = new Image(Objects.requireNonNull(Viewer.class.getResource(RugPath(rug.getColor().value))).toString());
        ImagePattern pattern = new ImagePattern(rugImg);
        bestRec.setFill(pattern);
        marrakech.boardMatrix[x1][y1].addPlacedRugs(rug);
        marrakech.boardMatrix[x2][y2].addPlacedRugs(rug);
        Timeline timelineThree = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (y1 != y2) {
                bestRec.setRotate(90);
                bestRec.setX(BoardStart_X + x1 * Tile_Size + Tile_Size / 2 - bestRec.getWidth() / 2);
                bestRec.setY(BoardStart_Y + y2 * Tile_Size - bestRec.getHeight() / 2);
            } else {
                bestRec.setX(BoardStart_X + x1 * Tile_Size);
                bestRec.setY(BoardStart_Y + y1 * Tile_Size);
            }
            information.setText("AI placed the Rug");
            root.getChildren().add(bestRec);
            marrakech.renewPlayersScore();
            renewPlayersVbox();
            if (marrakech.isGameOver()) {//6. may be last player, then Game is over
                stopAITurnInThird = true;
                stopAITurnInFirst = true;
                stopAITurnInSecond = true;
                Platform.runLater(() -> {
                    char winner = marrakech.getWinner();
                    stopAITurnInFirst = true;
                    showGameOverWindow(winner);
                });
            }
        }));
        timelineThree.playFromStart();
        timelineThree.setOnFinished(event -> {
            if (!stopAITurnInThird) {
                AIFourth(player);
            }
        });
    }

    private void AISecond(Player player) {
        stopAITurnInSecond = false;
        //3. pay if on else's rug
        Timeline timelineTwo = new Timeline(new KeyFrame(Duration.ONE, event -> {
            int paymentAmount = marrakech.getPaymentAmount(1);
            if (paymentAmount != 0) {//have to pay money
                var color = marrakech.getTileFromPos(marrakech.assam.getPosition()).getTopRug().getColor();
                Player playerToPay = marrakech.getPlayerFromColor(color);
                player.Pay(playerToPay, paymentAmount);
                if (player.isInGame()) {//after pay AI still in Game
                    System.out.println("---------------------------------");
                    System.out.println(marrakech.getCurrentPlayer() + " Have to pay Player" + (playerToPay.getPlayId() + 1) + " " + paymentAmount + " diahams");
                    information.setText("Have to pay Player" + (playerToPay.getPlayId() + 1) + " " + paymentAmount + " diahams");
                    marrakech.renewPlayersScore();
                    renewPlayersVbox();
                } else {//4. may be broken in no.3
                    stopAITurnInSecond = true;
                    marrakech.renewPlayersScore();
                    renewPlayersVbox();
                    if (marrakech.isGame_Over_OtherPlayersBroken()) {
                        stopAITurnInFirst = true;
                        char winner = marrakech.getWinner();
                        Platform.runLater(() -> showGameOverWindow(winner));
                        System.out.println("winner is a" + winner);
                    } else {
                        Platform.runLater(() -> PhaseFour(player));
                    }
                }
            }
        }));
        timelineTwo.playFromStart();
        timelineTwo.setOnFinished(event -> {
            if (!stopAITurnInSecond) {
                AIThird(player);
            }
        });
    }

    boolean stopAITurnInFirst = false;

    private void AIFirst(Player player) {
        stopAITurnInSecond = true;
        if (player.isInGame()) {
            if (!stopAITurnInFirst) {
                //1. first rotate Assam
                Timeline timelineFirst = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                    information.setText("AI's Turn");
                    marrakech.bestDirection(difficulty);
                    displayAssam();
                }),
                        //2. move Assam
                        new KeyFrame(Duration.seconds(1.5), event -> {
                            System.out.print("Assam moving information: From " + marrakech.assam.toString());
                            DieNum = Marrakech.rollDie();
                            displayDice(DieNum);
                            if (DieNum == 1) information.setText("AI: Assam moved " + DieNum + " step");
                            else information.setText("AI: Assam moved " + DieNum + " steps");
                            marrakech.assam.preMove(DieNum);
                            System.out.println(" moved to : " + marrakech.assam.toString());
                            System.out.println("---------------------------------");
                            displayAssam();
                        }));
                timelineFirst.playFromStart();
                timelineFirst.setOnFinished(event -> {
                    System.out.println("to AI second");
                    AISecond(player);
                });

            } else {//if AI is not in Game
                PhaseFour(player);
            }
        }

    }

    boolean allowDetectMouseClick = true;//allow player to set Assam orientation
    boolean allowDetectMouseMove = true;//allow player to move rug

    /**
     * put rug on the board
     * move mouse to move rug
     * use mouseScroll to rotate rug
     *
     * @param player the current player
     */
    private void placeRug(Player player) {
        System.out.println("Please put the rug");
        System.out.println("---------------------------------");

        marrakech.bestPlacement(1);
        System.out.println("---------------------------------");
        Rug rug = new Rug(player.getColor());

        Rectangle recRug = new Rectangle(PlayerStart_X, DiceStart_Y, 140, 70);
        Image rugImg = new Image(Objects.requireNonNull(Viewer.class.getResource(RugPath(rug.getColor().value))).toString());
        ImagePattern pattern = new ImagePattern(rugImg);
        recRug.setFill(pattern);
        root.getChildren().add(recRug);
        root.setOnMouseMoved(event -> {
            if (allowDetectMouseMove) {
                recRug.setX(event.getX() - recRug.getWidth() / 2);
                recRug.setY(event.getY() - recRug.getHeight() / 2);
            }
        });

        recRug.setOnScroll(event -> {
            double currentRotation = recRug.getRotate();
            if (event.getDeltaY() > 0) {
                currentRotation += 270;
            } else {
                currentRotation += 90;
            }
            recRug.setRotate(currentRotation);
        });

        root.setOnMouseClicked(event -> {
            if (allowDetectMouseClick) {
                double mouseX = event.getX();
                double mouseY = event.getY();
                int x1, y1, x2, y2;
                // Rotate vertically
                if ((int) recRug.getRotate() % 180 != 0) {
                    x1 = (int) ((mouseX - BoardStart_X) / Tile_Size);
                    x2 = x1;
                    y1 = (int) ((mouseY - Tile_Size / 1.75 - BoardStart_Y) / Tile_Size);
                    y2 = (int) ((event.getY() + Tile_Size / 1.75 - BoardStart_Y) / Tile_Size);

                }
                // Rotate horizontally
                else {
                    x1 = (int) ((mouseX - Tile_Size / 1.75 - BoardStart_X) / Tile_Size);
                    y1 = (int) ((mouseY - BoardStart_Y) / Tile_Size);
                    y2 = y1;
                    x2 = (int) ((event.getX() + Tile_Size / 1.75 - BoardStart_X) / Tile_Size);
                }
                IntPair intPair1 = new IntPair(x1, y1);
                IntPair intPair2 = new IntPair(x2, y2);
                System.out.println("you placed rug at " + intPair1 + " " + intPair2);
                System.out.println("---------------------------------");
                IntPair[] temp = new IntPair[]{intPair1, intPair2};
                IntPair[] origin = rug.getAbsolutePositions();
                rug.setRelativeSegmentPositions(temp);
                boolean flag = marrakech.isPlacementValid(rug);
                if (flag) {
                    allowDetectMouseMove = false;
                    marrakech.rugs.add(rug);
                    marrakech.boardMatrix[x1][y1].addPlacedRugs(rug);
                    marrakech.boardMatrix[x2][y2].addPlacedRugs(rug);
                    // Rotate vertically
                    if ((int) recRug.getRotate() % 180 != 0) {
                        recRug.setX(BoardStart_X + x1 * Tile_Size + Tile_Size / 2 - recRug.getWidth() / 2);
                        recRug.setY(BoardStart_Y + y2 * Tile_Size - recRug.getHeight() / 2);
                    }
                    // Rotate horizontally
                    else {
                        recRug.setX(BoardStart_X + x1 * Tile_Size);
                        recRug.setY(BoardStart_Y + y1 * Tile_Size);
                    }
                    recRug.setOnScroll(null);
                    allowDetectMouseClick = false;
                    allowRoAssam = true;
                    newRotation = 0;
                    PhaseFour(player);
                } else {
                    information.setText("Wrong position, place again!");
                    rug.setRelativeSegmentPositions(origin);
                }
                marrakech.renewPlayersScore();
                renewPlayersVbox();
                if (marrakech.isGame_Over()) {
                    char winner = marrakech.getWinner();
                    stopAITurnInFirst = true;
                    showGameOverWindow(winner);
                    System.out.println("winner is " + winner);
                }
            }
        });

    }

    boolean allowRoAssam = true;
    boolean allowClickAssam = true;

    private void displayAssamFromRotation(int rotation) {
        paneAssam.getChildren().remove(0);
        IntPair intPair = marrakech.assam.getPosition();
        int assamX = intPair.getY();
        int assamY = intPair.getX();
        Angle angle = Angle.getAngleFromValue(rotation);
        assert angle != null;
        char c = switch (angle) {
            case DEG_0 -> 'N';
            case DEG_90 -> 'E';
            case DEG_180 -> 'S';
            case DEG_270 -> 'W';
        };
        Image imageAssam = new Image(Objects.requireNonNull(Viewer.class.getResource(AssamPath(c))).toString());
        ImageView nimageView = new ImageView();
        nimageView.setImage(imageAssam);
        nimageView.setFitHeight(Tile_Size);
        nimageView.setFitWidth(Tile_Size);
        paneAssam.getChildren().add(nimageView);

        nimageView.setLayoutX(BoardStart_X + assamY * Tile_Size);
        nimageView.setLayoutY(BoardStart_Y + assamX * Tile_Size);
        paneAssam.getChildren().remove(nimageView);
        paneAssam.getChildren().add(nimageView);
    }

    /**
     * use mouseScroll to rotate Assam
     * and click mouse to set Assam
     */
    public void updateAssam() {
        System.out.println("Please use mouseScroll to Rotate Assam");
        System.out.println("---------------------------------");
        marrakech.bestDirectionForHuman();
        information.setText("Please use Scroll to Rotate Assam");
        newRotation = 0;
        allowDetectMouseClick = false;
        paneAssam.setOnScroll(event -> {
            if (allowRoAssam) {
                int rotate = marrakech.assam.getAngle().getValue();
                if (event.getDeltaY() > 0) {
                    newRotation += 270;
                } else if (event.getDeltaY() < 0) {
                    newRotation += 90;
                }
                displayAssamFromRotation(rotate + newRotation);
            }
        });
        paneAssam.setOnMouseClicked(event -> {
            if (allowClickAssam) {
                allowRoAssam = false;
                allowClickAssam = false;
                Transform transform = new Transform(newRotation);
                if (transform.getRotation() != null) {
                    allowRoAssam = false;
                    marrakech.rotateAssam(transform);
                    displayAssam();
                    allowDetectMouseClick = true;
                    information.setText("Please click the button to Roll Dice");
                    displayRollDiceControl();
                } else {
                    System.out.println("Illegal Rotation, Please rotate again");
                    information.setText("Illegal Rotation, rotate again!");
                    allowRoAssam = true;
                    allowClickAssam = true;
                    newRotation = 0;
                    displayAssam();
                }
            }
        });
    }

    /**
     * if player still in game, let him put rug, otherwise turn to PhaseFour
     *
     * @param player the current player
     */
    public void PhaseThree(Player player) {
        if (player.isInGame()) {
            allowDetectMouseMove = true;
            allowDetectMouseClick = true;
            placeRug(player);
        } else {
            PhaseFour(player);
        }
    }

    /**
     * count payment after move Assam and check if player is still in Game
     *
     * @param player the current player
     */
    public void PhaseTwo(Player player) {
        if (player.isInGame()) {
            displayAssam();
            int paymentAmount = marrakech.getPaymentAmount(1);
            if (paymentAmount != 0) {
                var color = marrakech.getTileFromPos(marrakech.assam.getPosition()).getTopRug().getColor();
                Player playerToPay = marrakech.getPlayerFromColor(color);
                player.Pay(playerToPay, paymentAmount);
                if (player.isInGame()) {
                    System.out.println(marrakech.getCurrentPlayer() + " Have to pay Player" + (playerToPay.getPlayId() + 1) + " " + paymentAmount + " diahams");
                    System.out.println("---------------------------------");
                    information.setText("Have to pay Player" + (playerToPay.getPlayId() + 1) + " " + paymentAmount + " diahams");
                    marrakech.renewPlayersScore();
                    renewPlayersVbox();
                    PhaseThree(player);
                } else {
                    marrakech.renewPlayersScore();
                    renewPlayersVbox();
                    if (marrakech.isGame_Over_OtherPlayersBroken()) {
                        stopAITurnInFirst = true;
                        char winner = marrakech.getWinner();
                        showGameOverWindow(winner);
                        System.out.println("winner is a" + winner);
                    }
                    PhaseFour(player);
                }
            } else {
                PhaseThree(player);
            }
        } else {
            PhaseFour(player);
        }
    }

    private void renewPlayersVbox() {
        for (int i = 0; i < marrakech.players.length; i++) {
            playervbox.getChildren().remove(i);
            playervbox.getChildren().add(i, createPlayerPane(marrakech.players[i]));
        }
    }

    /**
     * PhaseOne: to rotate Assam
     */
    public void PhaseOne() {
        var player = marrakech.getCurrentPlayer();
        allowDetectMouseMove = true;
        if (player.isInGame()) {
            newRotation = 0;
            updateAssam();
        } else {
            PhaseFour(player);
        }
    }

    /**
     * show information after GameOver
     */
    public void showGameOverWindow(char winner) {
        playervbox.getChildren().clear();
        displayPlayer();
        Alert gameoverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameoverAlert.setTitle("Game Over");
        gameoverAlert.setHeaderText("Congratulations!");
        //change Button text on alert
        Button button = (Button) gameoverAlert.getDialogPane().lookupButton(gameoverAlert.getButtonTypes().get(0));
        button.setText("Restart");
        if (winner == 't') {
            gameoverAlert.setContentText("Game is over , the result is tie.");
        } else {
            comp1110.ass2.Color playerColor = comp1110.ass2.Color.fromChar(winner);
            Player player = marrakech.getPlayerFromColor(playerColor);
            switch (winner) {
                case 'c' ->
                        gameoverAlert.setContentText("Game is over. PLAYER " + (player.getPlayId() + 1) + " (Cyan) is winner!");
                case 'r' ->
                        gameoverAlert.setContentText("Game is over. PLAYER " + (player.getPlayId() + 1) + " (Red) is winner!");
                case 'y' ->
                        gameoverAlert.setContentText("Game is over. PLAYER " + (player.getPlayId() + 1) + " (Yellow) is winner!");
                case 'p' ->
                        gameoverAlert.setContentText("Game is over. PLAYER " + (player.getPlayId() + 1) + " (Purple) is winner!");
            }
        }
        gameoverAlert.setOnCloseRequest(event -> restart());
        gameoverAlert.showAndWait();
    }

    public void restart() {
        System.out.println("New Game");
        root.getChildren().clear();
        this.diceBoard.getChildren().clear();
        this.playervbox.getChildren().clear();
        makeBackground();
        makeBoard();
        if (marrakech.players != null) Player.resetPlayerCount();
        allowRoAssam = true;
        allowClickAssam = true;
        allowDetectMouseClick = false;
        stopAITurnInSecond = false;
        stopAITurnInThird = false;
        stopAITurnInFirst = false;
        selectPlayerWithComWindow();
    }

    private void selectPlayerWithComWindow() {
        Stage separateStage = new Stage();
        separateStage.initModality(Modality.APPLICATION_MODAL);
        separateStage.initStyle(StageStyle.UTILITY);

        separateStage.setTitle("Player Setting");

        StackPane layout = new StackPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(15);

        Label playerLabel = new Label("Human:");
        Label computerLabel = new Label("Computer:");

        ChoiceBox<Integer> playerChoice = new ChoiceBox<>();
        playerChoice.getItems().addAll(1, 2, 3, 4);
        playerChoice.setValue(0);

        ChoiceBox<Integer> computerChoice = new ChoiceBox<>();
        computerChoice.getItems().addAll(0, 1, 2, 3, 4);
        computerChoice.setValue(0);

        RadioButton easyRadioButton = new RadioButton("easy");
        RadioButton mediumRadioButton = new RadioButton("medium");
        RadioButton hardRadioButton = new RadioButton("hard");
        ToggleGroup aiDifficultyGroup = new ToggleGroup();
        easyRadioButton.setToggleGroup(aiDifficultyGroup);
        mediumRadioButton.setToggleGroup(aiDifficultyGroup);
        hardRadioButton.setToggleGroup(aiDifficultyGroup);
        easyRadioButton.setSelected(true);

        Button startButton = new Button("Start");
        startButton.setDisable(true);

        playerChoice.setOnAction(event -> {
            int totalPlayers = playerChoice.getValue() + computerChoice.getValue();
            startButton.setDisable(totalPlayers > 4 || totalPlayers < 2 || playerChoice.getValue() <= 0);
        });

        computerChoice.setOnAction(event -> {
            int totalPlayers = playerChoice.getValue() + computerChoice.getValue();
            startButton.setDisable(totalPlayers > 4 || totalPlayers < 2 || playerChoice.getValue() <= 0);
        });

        startButton.setOnAction(event -> {
            System.out.println("RealPlayerNum: " + playerChoice.getValue() + "  ComputerNum: " + computerChoice.getValue());
            realPlayerNum = playerChoice.getValue();
            comPlayerNum = computerChoice.getValue();
            playerNum = realPlayerNum + comPlayerNum;
            RadioButton selectedRadioButton = (RadioButton) aiDifficultyGroup.getSelectedToggle();
            if (selectedRadioButton.equals(hardRadioButton)) difficulty = 2;
            else if (selectedRadioButton.equals(easyRadioButton)) difficulty = 0;
            else difficulty = 1;
            separateStage.close();
            newGame();
        });

        grid.add(playerLabel, 0, 0);
        grid.add(playerChoice, 1, 0);
        grid.add(computerLabel, 0, 1);
        grid.add(computerChoice, 1, 1);
        grid.add(easyRadioButton, 2, 1);
        grid.add(mediumRadioButton, 2, 2);
        grid.add(hardRadioButton, 2, 3);
        grid.add(startButton, 3, 3);
        layout.getChildren().add(grid);
        separateStage.setScene(new Scene(layout, 310, 150));
        separateStage.show();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Marrakech");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
        makeBackground();
        makeBoard();
        selectPlayerWithComWindow();
    }

    //add music and play music button
    public void music() {
        Button musicButton = new Button(" Music ");
        root.getChildren().add(musicButton);
        musicButton.setLayoutX(RestartButtonStart_X);
        musicButton.setLayoutY(PlayerStart_Y);
        String bgMusicURL = "assets/bg.mp3";
        Media bgMusic = new Media(Objects.requireNonNull(getClass().getResource(bgMusicURL)).toExternalForm());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);//set music play in a loop
        musicButton.setOnAction(event -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        });
    }
}
