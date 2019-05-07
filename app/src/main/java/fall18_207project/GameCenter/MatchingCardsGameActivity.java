package fall18_207project.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MatchingCardsGameActivity extends AppCompatActivity implements Observer, GameActivity {
    /**
     * The matchingGame.
     */
    private MatchingCards matchingCards;
    private GameManager gameManager;
    private String saveType;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> cardButtons;

    //Timer textView
    TextView mTvTimer;
    //Instance of Chronometer
    GameChronometer mChrono;
    //Thread for chronometer
    Thread mThreadChrono;
    Context mContext;


    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        if (matchingCards.isStartMode())
            initCardButtons();
        else
            updateGameTileButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));

        int counter = matchingCards.getCountMove();
        TextView count = findViewById(R.id.steps_id);
        count.setText("Step: " + counter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
        setContentView(R.layout.activity_matchingcard_game_main);

        initial();
        createGameTileButtons(MatchingCardsGameActivity.this);
        setTimerText();
        setGameView();
        if(matchingCards.isStartMode())
            addStartButtonListener();
        else setStartButtonUnClickable();
        addSaveButtonListener();
        addResetButtonListener();
    }

    public void setGameView(){
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(matchingCards.getMatchingBoard().getNumOfColumns());
        gridView.setGame(matchingCards);
        matchingCards.getMatchingBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / matchingCards.getMatchingBoard().getNumOfColumns();
                        columnHeight = displayHeight / matchingCards.getMatchingBoard().getNumOfRows();
                        display();
                    }
                });

    }

    public void setTimerText(){
        if (matchingCards.getElapsedTime() != 0) {
            mContext = this;
            mChrono = new GameChronometer(mContext,
                    System.currentTimeMillis() - matchingCards.getElapsedTime());
            mThreadChrono = new Thread(mChrono);
            mThreadChrono.start();
            mChrono.start();
        }
        mContext = this;
        mTvTimer = findViewById(R.id.time_id);
        if (mChrono == null) {
            mChrono = new GameChronometer(mContext);
            mThreadChrono = new Thread(mChrono);
            mThreadChrono.start();
            mChrono.start();
        }
    }

    void initial(){
        saveType = getIntent().getStringExtra("saveType");
        gameManager = saveType.equals("autoSave")? CurrentAccountController.getCurrAccount().getAutoSavedGames():
                saveType.equals("userSave")? CurrentAccountController.getCurrAccount().getUserSavedGames():
                        CurrentAccountController.getCurrAccount().getUserScoreBoard();
        String saveId = getIntent().getStringExtra("saveId");
        matchingCards = saveId == null? new MatchingCards(4):
                (MatchingCards) gameManager.getGame(getIntent().getStringExtra("saveId"));
    }


    /**
     * Update the text of tv_timer
     *
     * @param timeAsText the text to update tv_timer with
     */
    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTimer.setText(timeAsText);
            }
        });
    }

    private void setStartButtonUnClickable(){
        Button startButton = findViewById(R.id.StartMatchButton);
        startButton.setClickable(false);
        startButton.setText("Started");
    }

    private void addStartButtonListener() {
        final Button startButton = findViewById(R.id.StartMatchButton);
        startButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchingCards.setStartMode();
                startButton.setClickable(false);
                startButton.setText("Started");
                updateGameTileButtons();
            }
        }));

    }

    private void addResetButtonListener() {
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restart = new Intent(getApplicationContext(), MatchingCardsGameActivity.class);
                int size = matchingCards.getGameId();
                MatchingCards newMatchingCards = new MatchingCards(size);
                newMatchingCards.initialList = matchingCards.cloneCards(matchingCards.initialList);
                newMatchingCards.matchingBoard = new MatchingBoard(matchingCards.cloneCards(matchingCards.initialList), size);
                restart.putExtra("saveId", newMatchingCards.getSaveId());
                restart.putExtra("saveType", "autoSave");
                CurrentAccountController.getCurrAccount().getAutoSavedGames().addGame(newMatchingCards);
                startActivity(restart);
            }
        });
    }

    /**
     * Create the buttons for displaying the cards.
     *
     * @param context the context
     */
    public void createGameTileButtons(Context context) {
        MatchingBoard matchingBoard = matchingCards.getMatchingBoard();
        cardButtons = new ArrayList<>();
        for (int row = 0; row < matchingCards.getMatchingBoard().getNumOfRows(); row++) {
            for (int col = 0; col < matchingCards.getMatchingBoard().getNumOfColumns(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(matchingBoard.getCard(row, col).getBackground());
                this.cardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the cards.
     */
    public void updateGameTileButtons() {
        MatchingBoard matchingBoard = matchingCards.getMatchingBoard();
        int nextPos = 0;
        for (Button b : cardButtons) {
            int row = nextPos /matchingCards.getMatchingBoard().getNumOfRows();
            int col = nextPos % matchingCards.getMatchingBoard().getNumOfColumns();
            if(matchingBoard.getCard(row, col).isUp()) {
                b.setBackgroundResource(matchingBoard.getCard(row, col).getBackground());
                }
            else {
                b.setBackgroundResource(R.drawable.matching_front);
            }
            nextPos++;
        }
    }

    private void initCardButtons() {
        MatchingBoard matchingBoard = matchingCards.getMatchingBoard();
        int nextPos = 0;
        for (Button b : cardButtons) {
            int row = nextPos / matchingCards.getMatchingBoard().getNumOfRows();
            int col = nextPos % matchingCards.getMatchingBoard().getNumOfColumns();
            b.setBackgroundResource(matchingBoard.getCard(row, col).getBackground());
            nextPos++;
        }
    }

    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.saveGameButton);
        saveButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchingCards.updateElapsedTime(mChrono.getElapsedTime());
                CurrentAccountController.getCurrAccount().getUserSavedGames().addGame(matchingCards);
                CurrentAccountController.getCurrAccount().getProfile().updateTotalPlayTime(mChrono.getActualElapsedTime());
                mChrono.updateSavedTime();
                makeSavedMessage();
            }
        }));
    }

    private void makeSavedMessage() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        matchingCards.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();
        CurrentAccountController.getCurrAccount().getAutoSavedGames().addGame(matchingCards);
        CurrentAccountController.getCurrAccount().getProfile().updateTotalPlayTime(mChrono.getActualElapsedTime());
        mChrono.updateSavedTime();
        updateCurrAccount();
    }


    @Override
    protected void onStop() {
        super.onStop();
        matchingCards.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();
        CurrentAccountController.getCurrAccount().getAutoSavedGames().addGame(matchingCards);
        CurrentAccountController.getCurrAccount().getProfile().updateTotalPlayTime(mChrono.getActualElapsedTime());
        mChrono.updateSavedTime();
        updateCurrAccount();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateCurrAccount();
        switchToGameCentre();
    }

    public void switchToGameCentre() {
        Intent goToStart = new Intent(getApplicationContext(), MatchingCardStartActivity.class);
        startActivity(goToStart);
    }

    private void updateCurrAccount() {
        CurrentAccountController.writeData(MatchingCardsGameActivity.this);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
