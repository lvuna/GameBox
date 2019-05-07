package fall18_207project.GameCenter;

import android.content.Context;

public class SlidingTileStartingController extends GameStartController {
    SlidingTileStartingController(Context context){
        super(context);
    }

    SlidingTiles createGame(int num){
        return new SlidingTiles(num);
    }

    void userSignOut(){
        firebaseAuth.signOut();
    }
}

