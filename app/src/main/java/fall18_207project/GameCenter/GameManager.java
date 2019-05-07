package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;

abstract class GameManager implements Serializable {
    ArrayList<Game> gameList;

    GameManager() {
        this.gameList = new ArrayList<Game>();
    }

    ArrayList<Game> getAllGameList() {
        return this.gameList;
    }

    ArrayList<Game> getSavedGames(int gameId) {
        ArrayList<Game> savedGames = new ArrayList<Game>();
        for (Game someGame : this.gameList) {
            if (someGame.getGameId() == gameId) {
                savedGames.add(someGame);
            }
        }
        return savedGames;
    }

    abstract void addGame(Game newGame);

    boolean deleteGame(String saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId().equals(saveId)) {
                this.gameList.remove(game);
                return true;
            }
        }
        return false;
    }

    Game getGame(String saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId().equals(saveId)) {
                return game;
            }
        }
        return null;
    }

    boolean hasGame(String saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId().equals(saveId)) {
                return true;
            }
        }
        return false;
    }

    void clear() {
        this.gameList.clear();
    }
}
