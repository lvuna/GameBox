package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * The 2048 Game
 */
public class Game2048 extends Game implements GameFeature, Cloneable {
    /**
     * The direction of the swipe is left
     */
    public final static int LEFT = 1;
    /**
     * The direction of the swipe is right
     */
    public final static int RIGHT = 2;
    /**
     * The direction of the swipe is up
     */
    public final static int UP = 3;
    /**
     * The direction of the swipe is Down
     */
    public final static int DOWN = 4;

    /**
     * The ID to find blank_id
     */
    private final static int BLANK_ID = 25;
    /**
     * The score of the 2048 game
     */
    private int score;
    /**
     * A  stack of boards for saving
     */
    private Stack<Game2048Board> boardStack;
    /**
     * A board of game 2048
     */
    protected Game2048Board board;
    protected Board initialBoard;
    /**
     * The list of Game2048 Tiles
     */
    protected List<Game2048Tile> tiles;

    /**
     * Initialize the 2048 game  with gameid and Game 2048 Tiles
     */
    public Game2048() {
        this.gameId = 7;

        tiles = new ArrayList<>();

        final int numTiles = 16;
        Random r = new Random();
        int beginnum1 = (r.nextInt(1) + 1) * 2;
        int beginnum2 = (r.nextInt(1) + 1) * 2;

        tiles.add(new Game2048Tile(beginnum1 - 1));
        tiles.add(new Game2048Tile(beginnum2 - 1));
        for (int tileNum = 0; tileNum < numTiles - 2; tileNum++) {
            tiles.add(new Game2048Tile(BLANK_ID - 1));
        }
        Collections.shuffle(tiles);

        board = new Game2048Board(tiles, 4);

        this.endTime = 0;
        this.score = 0;
        this.boardStack = new Stack<>();
    }

    /**
     * a reset method
     *
     * @return a new game with brand new board
     */
    @Override
    Game2048 reset() {
        return new Game2048();
    }

    /**
     * The Clone method of Game2048 Tile for reset method
     */
    public List<Game2048Tile> cloneTiles() {
        List<Game2048Tile> returnTile = new ArrayList<>();
        for (Game2048Tile tile : tiles) {
            returnTile.add(tile);
        }
        return returnTile;
    }

    /**
     * return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * return the whole stack used for storing all the boards
     *
     * @return the boardStack that used for storing all the boards
     */
    public Stack<Game2048Board> getBoardStack() {
        return boardStack;
    }

    /**
     * @return the current board
     */
    public Game2048Board getBoard() {
        return board;
    }

    /**
     * @return the score under some certain algorithm
     */
    @Override
    int calculateScore() {
        if (!hasValidMove()) {
            return 0;
        } else {
            return score;
        }
    }

    /**
     * @param direction
     * @return if this direction is valid tap
     */
    @Override
    public boolean isValidTap(int direction) {
        return false;
    }

    /**
     * Method to check whether it is a dead board
     */
    public boolean hasValidMove() {
        boolean valid = false;
        int blankId = 25;
        for (int i = 0; i < board.getNumOfColumns(); i++) {
            for (int j = 0; j < board.getNumOfRows(); j++) {
                if (i + 1 < board.getNumOfColumns()) {
                    if (board.getTile(i + 1, j).getId() == blankId || board.getTile(i + 1, j).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }
                if (i - 1 >= 0) {
                    if (board.getTile(i - 1, j).getId() == blankId || board.getTile(i - 1, j).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }
                if (j + 1 < board.getNumOfColumns()) {
                    if (board.getTile(i, j + 1).getId() == blankId || board.getTile(i, j + 1).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }
                if (j - 1 >= 0) {
                    if (board.getTile(i, j - 1).getId() == blankId || board.getTile(i, j - 1).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * Left shift algorithm
     */
    private boolean leftShift(int row) {
        boolean check = false;
        for (int col = 0; col < board.getNumOfColumns() - 1; ++col) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row, col + 1).getId() != BLANK_ID) {
                board.swapTiles(row, col + 1, row, col);// if there is an empty string on the left of  non-empty string swipe left
                check = true;
            }
        }
        return check;
    }

    /**
     * Right shift has the same algorithm with left
     */
    private boolean rightShift(int row) {
        boolean check = false;
        for (int col = board.getNumOfColumns() - 1; col > 0; --col) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row, col - 1).getId() != BLANK_ID) {
                board.swapTiles(row, col, row, col - 1);
                check = true;
            }
        }
        return check;
    }

    /**
     * Down shift has the same algorithm with left
     */
    private boolean downShift(int col) {
        boolean check = false;
        for (int row = board.getNumOfRows() - 1; row > 0; --row) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row - 1, col).getId() != BLANK_ID) {
                board.swapTiles(row, col, row - 1, col);
                check = true;
            }
        }
        return check;
    }

    /**
     * upShift has the same algorithm with left
     */
    private boolean upShift(int col) {
        boolean check = false;
        for (int row = 0; row < board.getNumOfRows() - 1; ++row) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row + 1, col).getId() != BLANK_ID) {
                board.swapTiles(row, col, row + 1, col);
                check = true;
            }
        }
        return check;
    }

    /**
     * touch move method. return nothing but will do some changes to this game
     */
    @Override
    public void touchMove(int direction) {
        boolean check = false;
        boardStack.push((Game2048Board) board.clone());
        countMove++;
        int value;

        if (direction == LEFT) {
            for (int row = 0; row < board.getNumOfRows(); ++row) {
                // Shift everything left
                check = check | leftShift(row);
                // and again
                check = check | leftShift(row);
                // Merge
                for (int col = 0; col < board.getNumOfColumns() - 1; ++col) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row, col + 1).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row][col + 1] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                // left shift
                leftShift(row);
            }
        } else if (direction == RIGHT) {
            for (int row = 0; row < board.getNumOfRows(); ++row) {
                check = check | rightShift(row);
                check = check | rightShift(row);
                for (int col = board.getNumOfColumns() - 1; col > 0; --col) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row, col - 1).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row][col - 1] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                rightShift(row);
            }
        } else if (direction == DOWN) {
            for (int col = 0; col < board.getNumOfColumns(); ++col) {
                check = check | downShift(col);
                downShift(col);
                for (int row = board.getNumOfRows() - 1; row > 0; --row) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row - 1, col).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row - 1][col] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                downShift(col);
            }
        } else if (direction == UP) {
            for (int col = 0; col < board.getNumOfColumns(); ++col) {
                check = check | upShift(col);
                upShift(col);
                for (int row = 0; row < board.getNumOfRows() - 1; ++row) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row + 1, col).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row + 1][col] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                upShift(col);
            }
        }

        if (check) {
            List<ArrayList<Integer>> blanktiles = new ArrayList<>();
            for (int i = 0; i < board.getNumOfColumns(); i++) {
                for (int j = 0; j < board.getNumOfRows(); j++) {
                    if (board.getTile(i, j).getId() == BLANK_ID) {
                        ArrayList<Integer> coordinate = new ArrayList<>();
                        coordinate.add(i);
                        coordinate.add(j);
                        blanktiles.add(coordinate);
                    }
                }
            }

            int bound = blanktiles.size();
            Random rnd = new Random();
            int position = rnd.nextInt(bound);
            int position2 = rnd.nextInt(bound);
            int number = (rnd.nextInt(1) + 1) * 2;
            board.getTiles()[blanktiles.get(position).get(0)][blanktiles.get(position).get(1)] = new Game2048Tile(number - 1);
            board.swapTiles(blanktiles.get(position2).get(0), blanktiles.get(position2).get(1), blanktiles.get(position).get(0), blanktiles.get(position).get(1));
        }
        boardStack.push(board.clone());
    }

    /**
     * undo() method will take you to the last board/state.
     */
    public void undo() {
        if (!boardStack.isEmpty()) {
            boardStack.pop();
        }
        if (!boardStack.isEmpty()) {
            board = boardStack.pop();
        }
    }

    /**
     * the method that check if the game is solved/over.
     *
     * @return whether the game is over
     */
    @Override
    public boolean isSolved() {
        boolean check = false;
        for (int i = 0; i < board.getNUM_COLS(); i++) {
            for (int j = 0; j < board.getNUM_ROWS(); j++) {
                if (board.getTile(i, j).getId() == 2048) {
                    check = true;
                }
            }
        }
        return check;
    }
}
