package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class SlidingTile4Test {
    private SlidingTiles slidingTile;

    /**
     * Create a solved board, also means a list of tiles in order as their id.
     * @return a list of tiles in order
     */
    private List<SlidingTilesTile> makeTiles() {
        List<SlidingTilesTile> tiles = new ArrayList<>();
        final int numTiles = 4 * 4;
        for (int tileNum = 0; tileNum != numTiles - 1; tileNum++) {
            tiles.add(new SlidingTilesTile(tileNum));
        }
        tiles.add(new SlidingTilesTile(24));
        return tiles;
    }

    /**
     * Set up the SlidingTile so that we can use it when testing. Also, it's a solved board!
     */
    private void setUpCorrect() {
        slidingTile = new SlidingTiles(4);
        List<SlidingTilesTile> tiles = makeTiles();
        slidingTile.board = new SlidingTileBoard(tiles, 4);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingTile.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(slidingTile.isSolved());
        swapFirstTwoTiles();
        assertFalse(slidingTile.isSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, slidingTile.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingTile.getBoard().getTile(0, 1).getId());
        assertEquals(1, slidingTile.getBoard().getTile(0, 0)
                        .compareTo(slidingTile.getBoard().getTile(0, 1)));

        slidingTile.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTile.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingTile.getBoard().getTile(0, 1).getId());
        assertEquals(R.drawable.tile_2, slidingTile.getBoard().getTile(0, 0).getBackground());
        assertEquals(R.drawable.tile_1, slidingTile.getBoard().getTile(0, 1).getBackground());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, slidingTile.getBoard().getTile(3, 2).getId());
        assertEquals(25, slidingTile.getBoard().getTile(3, 3).getId());
        slidingTile.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(25, slidingTile.getBoard().getTile(3, 2).getId());
        assertEquals(15, slidingTile.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertFalse(slidingTile.isValidTap(0));
        assertTrue(slidingTile.isValidTap(11));
        assertTrue(slidingTile.isValidTap(14));
        assertFalse(slidingTile.isValidTap(15));
        assertFalse(slidingTile.isValidTap(10));
    }

    /**
     *  Test touchMove method and undo method.
     */
    @Test
    public void testTouchMoveAndUndo() {
        setUpCorrect();
        Tile blankTile = new Tile(24);
        slidingTile.touchMove(14);

        assertFalse(slidingTile.isSolved());
        assertTrue(slidingTile.isValidTap(13));
        slidingTile.touchMove(13);
        assertEquals(25, slidingTile.board.getTile(3, 1).getId());
        slidingTile.undo();
        assertNotEquals(25, slidingTile.board.getTile(3, 1).getId());
        assertEquals(25, slidingTile.board.getTile(3, 2).getId());
        slidingTile.undo();
        assertNotEquals(25, slidingTile.board.getTile(3, 1).getId());
        assertEquals(25, slidingTile.board.getTile(3, 3).getId());

        // It's weird to test isSolved here
        // because it's illegal to keep playing the game after it's solved(we setUpCorrect at the begining)
        // when you are actually playing the game, but just make sure it's still working.
        slidingTile.isSolved();

        slidingTile.touchMove(11);
        assertEquals(25, slidingTile.getBoard().getTile(2, 3).getId());

        slidingTile.touchMove(15);
        assertEquals(25, slidingTile.getBoard().getTile(3, 3).getId());
    }

//    /**
//     * Test clone method in Board so that it gives right board when we undo
//     */
//    @Test
//    public void testClone() {
//        setUpCorrect();
//
//        Board copyBoard = slidingTile.getBoard().clone();
//        assertEquals(copyBoard.toString(), slidingTile.getBoard().toString());
//    }

    /**
     * Test Tile in Board successfully initialized
     */
    @Test
    public void testGetTiles() {
        setUpCorrect();

        SlidingTilesTile[][] getTile = new SlidingTilesTile[4][4];
        List<SlidingTilesTile> tiles = makeTiles();
        Iterator<SlidingTilesTile> iter = tiles.iterator();
        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++) {
                getTile[row][col] = iter.next();
            }
        }

        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++) {
                assertEquals(getTile[row][col].getId(), slidingTile.getBoard().getTiles()[row][col].getId());
            }
        }
    }

    /**
     * Test toString method in SlidingTile.
     */
    @Test
    public void testSlidingTileToString() {
        setUpCorrect();

        SlidingTiles stCopy = new SlidingTiles(4);
        assertEquals(stCopy.toString(), slidingTile.toString());

        stCopy = new SlidingTiles(5);
        assertNotEquals(stCopy.toString(), slidingTile.toString());
    }

    /**
     * Test calculateScore method works fine.
     */
    @Test
    public void testCalculateScore() {
        setUpCorrect();
        slidingTile.endTime = 99;
        slidingTile.countMove = 699;
        assertEquals(8, slidingTile.calculateScore());
    }
}
