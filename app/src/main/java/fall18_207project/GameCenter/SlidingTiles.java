package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTiles extends Game implements Cloneable, GameFeature {

    /**
     * Manage a new shuffled board.
     */
    protected SlidingTileBoard board;
    protected List<SlidingTilesTile> tiles;

    SlidingTiles(int num) {
        this.gameId = num-2;
        tiles = new ArrayList<>();
        final int numTiles = num * num;
        for (int tileNum = 0; tileNum < numTiles - 1; tileNum++) {
            tiles.add(new SlidingTilesTile(tileNum));
        }
        tiles.add(new SlidingTilesTile(24));
        shuffleBoard(tiles, num);
        board =  new SlidingTileBoard(tiles, num);
        this.endTime = 0;
    }

    private void shuffleBoard(List<SlidingTilesTile> winBoard, int num) {
        int blank = winBoard.size() - 1;
        Random r = new Random();
        int moveWay = r.nextInt(4);
        int boundStep = 6 - r.nextInt(4);
        int move;
        int i = 0;
        while (i < boundStep) {
            move = moveWay == 0 ? blank + 1 : moveWay == 1 ? blank - 1 : moveWay == 2 ?
                    blank + num : moveWay == 3 ? blank - num : blank;
            if (0 <= move && move <= winBoard.size() - 1) {
                Collections.swap(winBoard, blank, move);
                blank = move;
            }
            moveWay = r.nextInt(4);
            i++;
        }
    }

    public List<SlidingTilesTile> cloneTiles(){
        List<SlidingTilesTile> returnTile = new ArrayList<>();
            for (SlidingTilesTile tile : tiles){
            returnTile.add(tile);
        }
        return returnTile;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean isSolved() {
        boolean solved = true;
        Iterator<Tile> it = board.iterator();
        int correctId = 1; // the correct id of tile in the position of winning condition*/
        while (it.hasNext()) {
            int curId = it.next().getId();
            if (correctId != board.numTiles()) {
                if (curId != correctId) {
                    solved = false;
                }
            }
            correctId++;
        }
        return solved;
    }

    @Override
    SlidingTiles reset() {
        return new SlidingTiles(this.getBoard().getNumOfRows());
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int row = position / board.getNumOfRows();
        int col = position % board.getNumOfColumns();
        int blankId = 25;
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumOfRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumOfColumns() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * there's no need to watch out for exception since it was already confirmed
     * the validness of the move before touchMove is called.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / board.getNumOfRows();
        int col = position % board.getNumOfColumns();
        int blankId = 25;
        countMove++;
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumOfRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumOfColumns() - 1 ? null : board.getTile(row, col + 1);
        if (checkBlankTile(below, blankId)) {
            board.swapTiles(row, col, row + 1, col);
            saveMove.push(position + board.getNumOfRows());
        } else if (checkBlankTile(above, blankId)) {
            board.swapTiles(row, col, row - 1, col);
            saveMove.push(position - board.getNumOfRows());
        } else if (checkBlankTile(left, blankId)) {
            board.swapTiles(row, col, row, col - 1);
            saveMove.push(position - 1);
        } else if (checkBlankTile(right, blankId)) {
            board.swapTiles(row, col, row, col + 1);
            saveMove.push(position + 1);
        }
    }

    /**
     * undo the move by calling touchmove to goback to previous state. Can keep pressing until
     * board get back to start of the game.
     */
    public boolean undo() {
        if (!saveMove.isEmpty()) {
            int position = saveMove.pop();
            touchMove(position);
            saveMove.pop();
            return true;
        }
        return false;
    }

    /**
     * Calculate the Score of the game.
     *
     * @return return the score of the game
     */
    public int calculateScore() {
        return Math.round(1400 / (countMove + 1) + 600 / (endTime + 1));
    }

    /**
     * Check if the tile is blank tile.
     *
     * @param tile    tile.
     * @param blankId blank tile id.
     * @return true if tile is blank false otherwise.
     */
    private boolean checkBlankTile(Tile tile, int blankId) {
        return (tile != null && tile.getId() == blankId);
    }

    SlidingTileBoard getBoard() {
        return this.board;
    }

    @Override
    public String toString() {
        return board.getNumOfColumns() + " x " + board.getNumOfRows() + " SlidingTiles";
    }

    @Override
    public boolean hasValidMove() {
        // Since it is always possible to slide some tiles, so it's always true
        return true;
    }
}
