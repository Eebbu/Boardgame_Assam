package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    // Height and width of each tile
    private static final double Tile_Size = 70;

    // The start of the board in the x-direction (ie: x = 0)
    private static final double START_X = 120.0;

    // The start of the board in the y-direction (ie: y = 0)
    private static final double START_Y = 30.0;

    // Pixel gap between the grey rectangles that indicates where the tiles are on
    // the board
    private static final double BOARD_TILE_SHADOW_GAP = 10;

    // how much the blue background extends past the tiles
    private static final int StrokeWidth = 6;

    private final Group root = new Group();

    private final Group board = new Group();

    private final double boardWidth = Marrakech.BOARD_WIDTH * (Tile_Size + StrokeWidth) + Marrakech.BOARD_WIDTH * BOARD_TILE_SHADOW_GAP;
    private final double boardHeight = Marrakech.BOARD_HEIGHT * (Tile_Size + StrokeWidth) + Marrakech.BOARD_HEIGHT * BOARD_TILE_SHADOW_GAP;

    private final Group controls = new Group();
    private TextField boardTextField;
    private Marrakech marrakech;

    private void newGame(String state) {
        this.marrakech = new Marrakech(state);

    }

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        // FIXME Task 5: implement the simple state viewer
        // creating rectangle to represent the blue background of the board
        board.getChildren().clear();
        Rectangle boardBack = new Rectangle(START_X - (BOARD_TILE_SHADOW_GAP / 2), START_Y - (BOARD_TILE_SHADOW_GAP / 2), boardWidth, boardHeight);
        boardBack.setFill(Color.FLORALWHITE);
        boardBack.setArcHeight(30.0d);
        boardBack.setArcWidth(30.0d);
        // adding the rectangle to the board group
        board.getChildren().add(boardBack);
        GridPane tileGrid = new GridPane();
        // tileGrid.setGridLinesVisible(true);
        tileGrid.setLayoutX(START_X);
        tileGrid.setLayoutY(START_Y);
        tileGrid.setHgap(BOARD_TILE_SHADOW_GAP);
        tileGrid.setVgap(BOARD_TILE_SHADOW_GAP);

        int indexOfBoardState = state.indexOf("B");
        String boardString = state.substring(indexOfBoardState + 1);
        String[] rugChunks = boardString.split("(?<=\\G...)");

        int rugIndex = 0;

        for (int j = 0; j < Marrakech.BOARD_WIDTH; j++) {
            for (int i = 0; i < Marrakech.BOARD_HEIGHT; i++) {

                // Dye the tile here
                char rugChar = rugChunks[rugIndex].charAt(0);
                var color = comp1110.ass2.Color.fromChar(rugChar);

                Rectangle tile = new Rectangle(Tile_Size, Tile_Size);
                tile.setFill(color.getFillColor());
                tile.setStrokeWidth(StrokeWidth);
                tile.setStroke(Color.GREY);
                tile.setOpacity(0.5);
                tile.setArcHeight(10.0d);
                tile.setArcWidth(10.0d);

                String rugID = rugChunks[rugIndex].substring(1);

                Label idLabel = new Label(rugID.equals("00") && rugChar == 'n' ? null : rugID);

                StackPane stackPane = new StackPane(tile, idLabel);

                tileGrid.add(stackPane, j, i);

                rugIndex++;
            }
        }

        board.getChildren().add(tileGrid);

        displayPlayer();
        displayAssam(state);
    }

    // TODO display Player state on right side
    void displayPlayer() {

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: rgba(173, 216, 230, 0.5);;-fx-background-radius: 10; -fx-border-radius: 10;");
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-alignment: center-left;");

        for (int i = 0; i < marrakech.players.length; i++) {
            String playerNumber = "Player " + (i + 1) + ":";
            String dirhams = "Dirhams: " + marrakech.players[i].getDirhams();
            String remainingRugs = "Remaining Rugs: " + marrakech.players[i].getRugsRemaining();

            Label playerLabel = new Label(playerNumber);
            Label dirhamsLabel = new Label(dirhams);
            Label remainingRugsLabel = new Label(remainingRugs);

            gridPane.add(playerLabel, 0, i);
            gridPane.add(dirhamsLabel, 1, i);
            gridPane.add(remainingRugsLabel, 2, i);

        }
        vbox.getChildren().add(gridPane);
        root.getChildren().add(vbox);
        vbox.setLayoutX(800);
        vbox.setLayoutY(50);

    }

    void displayAssam(String state) {
        // place Assam
        int assamXPosition = state.indexOf("A") + 1;
        int assamX = Integer.parseInt(state.substring(assamXPosition, assamXPosition + 1));
        int assamY = Integer.parseInt(state.substring(assamXPosition + 1, assamXPosition + 2));
        String direction = state.substring(assamXPosition + 2, assamXPosition + 3);
        int rotateAngle = switch (direction) {
            case "N" -> 180;
            case "E" -> 270;
            case "W" -> 90;
            default -> 0;
        };

        Image image = new Image(Objects.requireNonNull(Viewer.class.getResource("assets/Assam.png")).toString());
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(Tile_Size + StrokeWidth);
        imageView.setImage(image);
        imageView.setLayoutX(START_X + assamX * (Tile_Size + StrokeWidth + BOARD_TILE_SHADOW_GAP));
        imageView.setLayoutY(START_Y + assamY * (Tile_Size + StrokeWidth + BOARD_TILE_SHADOW_GAP));
        imageView.setRotate(rotateAngle);
        board.getChildren().remove(imageView);
        board.getChildren().add(imageView);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            newGame(boardTextField.getText());
            displayState(boardTextField.getText());
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        root.getChildren().add(board);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
