package fall18_207project.GameCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHistoryActivity extends Activity {
    private UserHistoryController mController = new UserHistoryController(UserHistoryActivity.this);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    Intent tmp1 = new Intent(UserHistoryActivity.this, GameCentreActivity.class);
                    startActivity(tmp1);
                    break;
                case R.id.navigation_user_history:
                    break;
                case R.id.navigation_global_scoreboard:
                    Intent tmp2 = new Intent(UserHistoryActivity.this, GlobalScoreBoardActivity.class);
                    startActivity(tmp2);
                    break;
            }
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.historyView);
        final List<Map<String, Object>> list = new ArrayList<>();
        final ArrayList<Game> finalGameList = new ArrayList<>();
        mController.getData(list, finalGameList,0);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.user_history_item, new String[]{"gameId", "score"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);

        Spinner spinner = findViewById(R.id.spinner);
        String[] mItems = {"ST 3 X 3", "ST 4 x 4", "ST 5 X 5", "MC 4 x 4", "MC 5 x 5", "MC 6 x 6", "2048"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                list.clear();
                finalGameList.clear();
                mController.getData(list, finalGameList,pos+1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        scoreBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game selectedGame = finalGameList.get(position);
                selectedGame.reset();
                CurrentAccountController.getCurrAccount().getAutoSavedGames().addGame(selectedGame);
                int i = selectedGame.getGameId();
                goToDifferentGames(i, selectedGame);
            }
        });
    }

    private void goToDifferentGames(int id, Game selectedGame){
        Intent goToGame = id <= 3? new Intent(getApplicationContext(), SlidingTileGameActivity.class):
                id <= 6? new Intent(getApplicationContext(), MatchingCardsGameActivity.class):
                        new Intent(getApplicationContext(), Game2048Activity.class);
        goToGame.putExtra("saveType", "userHistory");
        goToGame.putExtra("saveId", selectedGame.getSaveId());
        startActivity(goToGame);
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateCurrAccount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateCurrAccount();
    }

    private void updateCurrAccount() {
        mController.updateCurrAccount();
    }
}