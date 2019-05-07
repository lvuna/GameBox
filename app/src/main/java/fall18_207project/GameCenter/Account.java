package fall18_207project.GameCenter;

import java.io.Serializable;

/**
 * Account class that stores a user.
 */
public class Account implements Serializable {
    private String email;
    private String userName;
    private String password;
    private GameManager userSavedGames;
    private GameManager autoSavedGames;
    private Profile profile;
    private GameManager userScoreBoard;

    public Account(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.profile = new Profile();
        this.userSavedGames = new NonDuplicateGameManager();
        this.autoSavedGames = new NonDuplicateGameManager();
        this.userScoreBoard = new NonDuplicateGameManager();
    }

    public GameManager getUserSavedGames() {
        return this.userSavedGames;
    }

    public GameManager getAutoSavedGames() {
        return this.autoSavedGames;
    }

    public GameManager getUserScoreBoard() {
        return this.userScoreBoard;
    }

    public String getEmail() {
        return this.email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return this.userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    void setPassword(String newPass) {
        this.password = newPass;
    }

    public Profile getProfile() {
        return profile;
    }
}
