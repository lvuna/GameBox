package fall18_207project.GameCenter;

import android.content.Context;

public class GameFinishController {
    private Context mContext;

    GameFinishController(Context context) {
        this.mContext = context;
    }

    String updateFinishedGame(String saveId) {
        String result;
        Game finishedGame = CurrentAccountController.getCurrAccount()
                .getAutoSavedGames().getGame(saveId);
        result = Integer.toString(finishedGame.calculateScore());
        processFinishedGame(finishedGame, saveId);
        return result;
    }

    private void processFinishedGame(Game finishedGame, String saveId) {
        String currEmail = CurrentAccountController.getCurrAccount().getEmail();
        // Update global score board
        CurrentAccountController.getAccountManager().getGlobalScoreBoard().updateScore(currEmail, finishedGame);
        // Update user score board
        CurrentAccountController.getCurrAccount().getUserScoreBoard().addGame(finishedGame);
        // Delete game from user's autoSavedGameList
        CurrentAccountController.getCurrAccount().getAutoSavedGames().deleteGame(saveId);
    }

    void updateCurrAccount() {
        CurrentAccountController.writeData(mContext);
    }

}
