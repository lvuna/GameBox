package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Cloneable, Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int numOfRows;

    /**
     * The number of rows.
     */
    private int numOfColumns;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numOfRows * numOfColumns
     *
     * @param tiles the tiles for the board
     */
    public Board(List<? extends Tile> tiles, int size) {
        this.numOfColumns = size;
        this.numOfRows = size;

        this.tiles = new Tile[size][size];
        Iterator<?> iter = tiles.iterator();

        for (int row = 0; row != this.getNumOfColumns(); row++) {
            for (int col = 0; col != this.getNumOfColumns(); col++) {
                this.tiles[row][col] = (Tile) iter.next();
            }
        }
    }

    public int getNumOfColumns() {
        return numOfColumns;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return numOfRows * numOfColumns;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile t1 = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = t1;
        setChanged();
        notifyObservers();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfColumns; col++) {
                temp.append(" ").append(this.getTile(row, col).getId());
            }
        }
        return "Board: { " +
                "tiles = [" + temp +
                " ]" + " }";
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }


    /**
     * The iterator class for Board's iterator.
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The position
         */
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position != numOfRows * numOfColumns;
        }

        @Override
        public Tile next() {
            int row = position / numOfRows;
            int col = position % numOfColumns;
            Tile nextTile = tiles[row][col];
            position++;
            return nextTile;
        }
    }
}
