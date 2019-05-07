package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    private SlidingTiles slidingTiles;
    private MatchingCards matchingCards;

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
     * Make a list of cards in order of "AA22...88"
     * @return a list of cards in order of their id
     */
    private List<Card> makeCards() {
        int n = 4;
        List<Card> cards = new ArrayList<>();
        int numTiles = n * n;
        for (int i = 0; i < numTiles; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }

    /**
     * set up with tiles and cards created above
     */
    private void setUpCorrect() {
        List<SlidingTilesTile> tiles = makeTiles();
        List<Card> cards = makeCards();
        matchingCards = new MatchingCards(4);
        slidingTiles = new SlidingTiles(4);
        slidingTiles.board = new SlidingTileBoard(tiles, 4);
        matchingCards.matchingBoard = new MatchingBoard(cards, 4);
    }

    @Test
    public void testGameID() {
        setUpCorrect();
        SlidingTiles stCopy = new SlidingTiles(4);

        assertNotEquals(slidingTiles.getGameId(), matchingCards.getGameId());
        assertEquals(stCopy.getGameId(), slidingTiles.getGameId());
        assertNotEquals(slidingTiles, matchingCards);
    }

    @Test
    public void testSaveID() {
        setUpCorrect();
        String savedID = slidingTiles.getSaveId();

        assertNotEquals(savedID, matchingCards.getSaveId());

        setUpCorrect(); // create a new SlidingTile game, and compare saveID

        assertNotEquals(savedID, slidingTiles.getSaveId());
        // should be different,although they have same size
    }

    @Test
    public void testToString() {
        setUpCorrect();

        assertNotEquals(Game.class.toString(), slidingTiles.toString());
        assertNotEquals(Game.class.toString(), matchingCards.toString());
        assertNotEquals(slidingTiles.toString(), matchingCards.toString());
    }

    @Test
    public void testTheTimer() {
        setUpCorrect();

        assertEquals(0, slidingTiles.getElapsedTime());

        slidingTiles.updateElapsedTime(1000);
        assertNotEquals(0, slidingTiles.getElapsedTime());
        assertEquals(1000, slidingTiles.getElapsedTime());
        assertNotEquals(slidingTiles.getElapsedTime(), matchingCards.getElapsedTime());

        slidingTiles.resetElapsedTime();
        assertEquals(0, slidingTiles.getElapsedTime());
    }

    @Test
    public void testHasValidMove() {
        setUpCorrect();

        assertTrue(slidingTiles.hasValidMove());
        assertTrue(matchingCards.hasValidMove());

        for (int i = 0; i < 16; i++) {
            matchingCards.touchMove(i);
        }
        assertTrue(matchingCards.isSolved());
        assertFalse(matchingCards.hasValidMove());
    }
}
