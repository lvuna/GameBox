package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SlidingTileBoard extends Board {

    /**
     * The number of rows.
     */
    private int NUM_ROWS;
    /**
     * The number of rows.
     */
    private int NUM_COLS;
    /**
     * The tiles on the board in row-major order.
     */
    private SlidingTilesTile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public SlidingTileBoard(List<SlidingTilesTile> tiles, int size) {
        super( tiles, size);
        this.NUM_COLS = size;
        this.NUM_ROWS = size;
        this.tiles = new SlidingTilesTile[size][size];

        Iterator<SlidingTilesTile> iter = tiles.iterator();

        for (int row = 0; row != this.getNumOfRows(); row++) {
            for (int col = 0; col != this.getNumOfColumns(); col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }
}
