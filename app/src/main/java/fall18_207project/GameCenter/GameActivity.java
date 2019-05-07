package fall18_207project.GameCenter;

import android.content.Context;

public interface GameActivity {
    void updateTimerText(final String timeAsText);

    void display();

    void createGameTileButtons(Context context);

    void updateGameTileButtons();
}
