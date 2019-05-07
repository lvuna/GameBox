package fall18_207project.GameCenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class MatchingCardStartController extends GameStartController{

    MatchingCardStartController(Context context){
        super(context);
    }

    MatchingCards createGame(int num){
        return new MatchingCards(num);
    }
}
