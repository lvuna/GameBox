package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchingCard4Test {
    private MatchingCards matchCards;

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
     * initialize a matchingboard with first 12 cards used
     */
    private void setUpCorrect() {
        matchCards = new MatchingCards(4);
        List<Card> cards = makeCards();
        matchCards.matchingBoard = new MatchingBoard(cards, 4);

        // Since I makecards in order, its guaranteed when i do the following loop, the two cards will match
        for (int i = 0; i<6; i++){
            matchCards.touchMove(2 * i);
            matchCards.touchMove(2 * i+1);
        }
    }

    /**
     * Test whether isSolved() return expected value
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();

        assertEquals(15, matchCards.getMatchingBoard().getCard(3, 2).getId());
        assertEquals(16, matchCards.getMatchingBoard().getCard(3, 3).getId());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 2).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 3).isUsed());
//        assertEquals(6, matchCards.getMatch());

        matchCards.touchMove(14);
        matchCards.touchMove(15);
        assertFalse(matchCards.isSolved());
//        assertEquals(7,matchCards.getMatch());

        matchCards.touchMove(13);
        matchCards.touchMove(12);
        assertTrue(matchCards.isSolved());
//        assertEquals(8, matchCards.getMatch());
    }

    /**
     * Test isUsed method in MatchingCards
     */
    @Test
    public void testIsUsed() {
        setUpCorrect();

        assertTrue(matchCards.getMatchingBoard().getCard(0, 0).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 3).isUsed());

        matchCards.touchMove(14);
        matchCards.touchMove(15);
        assertTrue(matchCards.getMatchingBoard().getCard(3, 3).isUsed());
    }

    /**
     * Test IsMatched method in MatchingCards
     */
    @Test
    public void testIsMatched() {
        setUpCorrect();

        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 2));
        assertEquals(-1, matchCards.getPrePos());

        assertEquals(-1, matchCards.getMatchingBoard().getCard(3, 3)
                .compareTo(matchCards.getMatchingBoard().getCard(3, 2)));

        matchCards.touchMove(15);
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 3));
        assertTrue(matchCards.isMatched(3, 2));
    }

    /**
     * test getCountMove method. It should continuously incrementing by 1,
     * although cards not match
     */
    @Test
    public void testGetCountMove() {
        setUpCorrect();

        assertEquals(12, matchCards.getCountMove());

        matchCards.touchMove(14);
        assertEquals(13, matchCards.getCountMove());

        matchCards.touchMove(15);
        assertEquals(14, matchCards.getCountMove());
    }

    /**
     * test isValidTap method.
     * There are two parts of it:
     * 1. When StartMode is true, any tap is not allowed
     * 2. When StartMode is false, only taps on unused card are allowed
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();

        assertFalse(matchCards.isValidTap(0));
        assertFalse(matchCards.isValidTap(15));
        assertFalse(matchCards.isValidTap(14));
        assertTrue(matchCards.isStartMode());

        matchCards.setStartMode(); // make StartMode false, which means start the game
        assertFalse(matchCards.isStartMode());
        assertTrue(matchCards.isValidTap(15));

        matchCards.touchMove(15);
        assertTrue(matchCards.isValidTap(15));
        assertTrue(matchCards.isValidTap(14));
    }

    /**
     * Test isUp method correctly shows whether a card is up or not.
     */
    @Test
    public void testIsUp() {
        setUpCorrect();

        assertEquals(matchCards.getMatchingBoard().getCard(3, 3).isUp(),
                matchCards.getMatchingBoard().getCard(3, 2).isUp());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 3).isUp());

        matchCards.touchMove(15);
        assertNotEquals(matchCards.getMatchingBoard().getCard(3, 3).isUp(),
                matchCards.getMatchingBoard().getCard(3, 2).isUp());
        assertTrue(matchCards.getMatchingBoard().getCard(3, 3).isUp());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 2).isUp());

        matchCards.touchMove(14);
        assertTrue(matchCards.getMatchingBoard().getCard(3, 3).isUp());
        assertTrue(matchCards.getMatchingBoard().getCard(3, 2).isUp());
    }

    /**
     * Test the card has right backID
     */
    @Test
    public void testBackId() {
        setUpCorrect();

        assertEquals(R.drawable.p1, matchCards.getMatchingBoard().getCard(0, 0).getBackground());
        assertEquals(R.drawable.p16, matchCards.getMatchingBoard().getCard(3, 3).getBackground());
    }
}
