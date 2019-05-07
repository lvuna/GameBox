package fall18_207project.GameCenter;

import android.content.Context;

public class Game2048StartController extends GameStartController {

    Game2048StartController(Context context) {
        super(context);
    }

    Game2048 createGame() {
        return new Game2048();
    }
}
