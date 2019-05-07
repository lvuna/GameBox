package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameManagerTest {
    private GameManager gameManager = new GameManager() {
        @Override
        void addGame(Game newGame) {
            gameManager.gameList.add(newGame);
        }
    };

    /**
     * add new games as below.
     */
    private void addSomeGames() {
        List<Game> temp = new ArrayList<Game>(Arrays.asList(
                new SlidingTiles(3),
                new SlidingTiles(3),
                new SlidingTiles(4),
                new MatchingCards(4),
                new MatchingCards(5),
                new Game2048()));
        for (int i = 0; i < 6; i ++) {
            gameManager.addGame(temp.get(i));
        }
    }

    /**
     * see if we can save the game into the gameManager when we add a game.
     */
    @Test
    public void testGameList() {
        assertTrue(gameManager.gameList.isEmpty());

        gameManager.addGame(new SlidingTiles(4));
        assertEquals(2 ,gameManager.gameList.get(0).getGameId());
        assertEquals(1, gameManager.gameList.size());
        assertNotNull(gameManager.gameList.get(0).getSaveId());
    }

    /**
     * Test whether getAllGameList() method giving the gameList(Actually not really necessary here)
     */
    @Test
    public void testGetAllGameList() {
        addSomeGames();
        List<Game> games = gameManager.getAllGameList();
        // Check if each index match gameID as expected
        assertEquals(1, games.get(0).getGameId());
        assertEquals(1, games.get(1).getGameId());
        assertNotEquals(games.get(0).getSaveId(), games.get(1).getSaveId());

        assertEquals(2, games.get(2).getGameId());
    }

    /**
     * Test whethere getSavedGame() method only giving the right games with right gameID
     */
    @Test
    public void testGetSavedGame() {
        addSomeGames();

        // there are 2 games with this ID! (aka. SlidingTile(3))
        assertEquals(2, gameManager.getSavedGames(1).size());
        // No game with gameID == 10(yet!)
        assertEquals(0, gameManager.getSavedGames(10).size());
    }

    /**
     * Test whether deleteGame() method only deleting the game with right saveID
     */
    @Test
    public void testDeleteGame() {
        addSomeGames();

        // Since saveID is randomly created, I dont have other ways to delete this game......
        gameManager.deleteGame(gameManager.gameList.get(0).saveId);
        // although there are 2 boards with same size(3), they dont share saveID,
        // there will be only 1 game with gameID == 1 left.
        assertEquals(1, gameManager.getSavedGames(1).size());

        // now index(0) is the second SlidingTile(3), after this there should be 0 left
        gameManager.deleteGame(gameManager.gameList.get(0).saveId);
        assertEquals(0, gameManager.getSavedGames(1).size());

        // it cannot be length of 1, so it doesnt exist
        assertFalse(gameManager.deleteGame("0"));
    }

    /**
     * Test if clear() method clearing the list properly
     */
    @Test
    public void testClear() {
        addSomeGames();
        gameManager.clear();

        assertEquals(0, gameManager.getSavedGames(0).size());
        assertEquals(0, gameManager.getSavedGames(1).size());
        assertEquals(0, gameManager.getAllGameList().size());
    }

    /**
     * Test whether hasGame() method works..
     */
    @Test
    public void testHasGame() {
        addSomeGames();

        // save the saveID of first game
        String savedID = gameManager.gameList.get(0).saveId;
        assertTrue(gameManager.hasGame(savedID));

        // Now delete it
        gameManager.deleteGame(savedID);
        assertFalse(gameManager.hasGame(savedID));
    }

    /**
     * Test whether getGame giving the right game with right saveID.
     */
    @Test
    public void testGetGame() {
        addSomeGames();
        String savedID = gameManager.gameList.get(0).saveId;
        assertNotNull(gameManager.getGame(savedID));
        assertNull(gameManager.getGame("0"));

        gameManager.deleteGame(savedID);
        assertNull(gameManager.getGame(savedID));
    }
}
