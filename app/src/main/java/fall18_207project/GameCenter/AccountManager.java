package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages all accounts. Emails are unique, but userNames are not.
 */
public class AccountManager implements Serializable {

    private Map<String, Account> accountMap;
    private GlobalScoreBoard globalScoreBoard;

    public AccountManager() {
        accountMap = new HashMap<>();
        globalScoreBoard = new GlobalScoreBoard();
    }

    public GlobalScoreBoard getGlobalScoreBoard() {
        return globalScoreBoard;
    }

    public boolean addAccount(Account newAccount) {
        if (accountMap.containsKey(newAccount.getEmail())) {
            return false;
        }
        accountMap.put(newAccount.getEmail(), newAccount);
        return true;
    }

    public Account getAccount(String email) {
        return accountMap.get(email);
    }

    boolean removeAccount(String email) {
        return accountMap.remove(email) != null;
    }

    boolean validate(String email, String password) {
        Account targetAccount = getAccount(email);
        if (targetAccount != null) {
            return targetAccount.getPassword().equals(password);
        }
        return false;
    }
}
