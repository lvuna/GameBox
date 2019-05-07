package fall18_207project.GameCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GlobalScoreBoardActivity extends Activity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    Intent tmp1 = new Intent(GlobalScoreBoardActivity.this, GameCentreActivity.class);
                    startActivity(tmp1);
                    break;
                case R.id.navigation_user_history:
                    Intent tmp2 = new Intent(GlobalScoreBoardActivity.this, UserHistoryActivity.class);
                    startActivity(tmp2);
                    break;
                case R.id.navigation_global_scoreboard:
                    break;
            }
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.scoreBoardView);
        final List<Map<String, Object>> list = new ArrayList<>();
        getData(list, 1);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.scoreboard_item, new String[]{"user", "score"},
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
                getData(list, pos + 1);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void getData(List<Map<String, Object>> list, int gameId) {
        AccountManager currAccountManager = CurrentAccountController.getAccountManager();
        GlobalScoreBoard globalScoreBoard = currAccountManager.getGlobalScoreBoard();

        ArrayList<Game> games = globalScoreBoard.getSortedGames(gameId);
        ArrayList<String> emails = globalScoreBoard.getSortedEmails(gameId);

        Collections.reverse(games);
        Collections.reverse(emails);
        for (int i = 0; i < emails.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("user", currAccountManager.getAccount(emails.get(i)).getUserName());
            map.put("score", games.get(i).calculateScore());
            list.add(map);
        }
    }
}
