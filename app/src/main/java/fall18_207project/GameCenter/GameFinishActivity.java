package fall18_207project.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//test comment here
public class GameFinishActivity extends AppCompatActivity {
    private GameFinishController mController = new GameFinishController(GameFinishActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finish);
        setScoreText();
        addReturnButtonListener();
    }

    public void setScoreText() {
        TextView tvResult = findViewById(R.id.textView2);
        String saveId = getIntent().getStringExtra("saveId");
        tvResult.setText(mController.updateFinishedGame(saveId));
    }

    private void addReturnButtonListener() {
        Button returnToScreen = findViewById(R.id.backToGamebtn);
        returnToScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentre();
            }
        });
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

    public void switchToGameCentre() {
        Intent returnToMainScreen = new Intent(getApplicationContext(), GameCentreActivity.class);
        startActivity(returnToMainScreen);
    }
}
