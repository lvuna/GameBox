package fall18_207project.GameCenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHistoryController {
    private List<Map<String, Object>> list;
    private ArrayList<Game> playGame;
    private Context mContext;
    UserHistoryController(Context context){
        mContext = context;
    }

    public void getData(List<Map<String, Object>> list, ArrayList<Game> playGame, int id) {
        if (CurrentAccountController.getCurrAccount() != null) {
            ArrayList<Game> gameList = CurrentAccountController.getCurrAccount().getUserScoreBoard().getAllGameList();
            for (int i = 0; i < gameList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                if (gameList.get(i).gameId ==id){
                    playGame.add(gameList.get(i));
                    map.put("gameId", gameList.get(i).getTime());
                    map.put("score",  gameList.get(i).calculateScore());
                    list.add(map);
                }
            }
            this.list = list;
            this.playGame = playGame;
        }
    }

    public void addAutoSaveGame(Game selectedGame){
        CurrentAccountController.getCurrAccount().getAutoSavedGames().addGame(selectedGame);
        updateCurrAccount();
    }

    public List<Map<String, Object>> getList(){
        return list;
    }

    public ArrayList<Game> getPlayGame() {
        return playGame;
    }

    void updateCurrAccount() {
        CurrentAccountController.writeData(mContext);
    }
}
