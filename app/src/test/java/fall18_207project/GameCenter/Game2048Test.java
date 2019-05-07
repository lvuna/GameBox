package fall18_207project.GameCenter;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class Game2048Test {
    private Game2048 game2048;

    /**
     *       _________________________________
     *      |   8     16     512     1024   |
     *     |   32     64     128      0    |
     *    |    64    128     256    1024  |
     *   |    128    256    512     256  |
     *   --------------------------------
     *   # if you scroll up or down, win the game!
     *   # if you scroll right, dead game!
     * @return a list of tiles looks like the order above
     */
    private List<Game2048Tile> makeTiles() {
        List<Integer> temp = new ArrayList<>(Arrays.asList(
                        7, 15, 511, 1023,
                        31, 63, 127, 24,
                        63, 127, 255, 1023,
                        127, 255, 511, 255));
        List<Game2048Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            tiles.add(new Game2048Tile(temp.get(i)));
        }
        return tiles;
    }

    /**
     * make a new board with tiles created above   (Line 15 --- Line 20)
     * Initialize a new Game2048 with that board.
     */
    private void setUpCorrect() {
        game2048 = new Game2048();
        List<Game2048Tile> tiles = makeTiles();
        game2048.board = new Game2048Board(tiles, 4);
    }

    /**
     * Test isSolved method with the tiles i created above
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();

        assertFalse(game2048.isSolved());

        game2048.touchMove(Game2048.LEFT);
        assertFalse(game2048.isSolved());

        game2048.touchMove(Game2048.UP);
        assertTrue(game2048.isSolved());

        // still solved since 2048 tile won't disappear
        game2048.touchMove(Game2048.DOWN);
        assertTrue(game2048.isSolved());
    }

    /**
     * test hasValidMove method returns right value based on possible moves
     */
    @Test
    public void testHasValidMove() {
        setUpCorrect();

        assertTrue(game2048.hasValidMove());

        game2048.touchMove(Game2048.RIGHT);
        assertFalse(game2048.hasValidMove());
    }

    /**
     * Test undo method giving the right board when using it
     */
    @Test
    public void testUndo() {
        setUpCorrect();
        Game2048Board boardCopy = new Game2048Board(makeTiles(), 4);
        Game2048 gm2048Copy = new Game2048();
        gm2048Copy.board = boardCopy;

        game2048.touchMove(Game2048.RIGHT);
        assertNotEquals(gm2048Copy.getBoardStack(), game2048.getBoardStack());

        game2048.undo();
        assertEquals(boardCopy.toString(), game2048.getBoard().toString());
        assertEquals(gm2048Copy.getBoardStack(), game2048.getBoardStack());
    }

    /**
     * Test CalculateScore method giving right score
     */
    @Test
    public void testScore() {
        setUpCorrect();

        game2048.touchMove(Game2048.UP);
        assertEquals(2048, game2048.calculateScore());

        setUpCorrect();
        game2048.touchMove(Game2048.RIGHT);
        assertEquals(0, game2048.calculateScore());
    }

    @Test
    public void testIsValidTap() {
        setUpCorrect();

        assertFalse(game2048.isValidTap(Game2048.UP));
        assertFalse(game2048.isValidTap(Game2048.DOWN));
        assertFalse(game2048.isValidTap(Game2048.LEFT));
        assertFalse(game2048.isValidTap(Game2048.RIGHT));
        assertFalse(game2048.isValidTap(5));
    }
}
