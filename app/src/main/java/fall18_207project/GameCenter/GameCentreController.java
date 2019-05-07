package fall18_207project.GameCenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameCentreController {

    private Context mContext;

    GameCentreController(Context context) {
        mContext = context;
    }

    void updateProfile(int id, String update) {
        if (CurrentAccountController.getCurrAccount() != null) {
            if (id == R.id.nav_reset) {
                CurrentAccountController.getCurrAccount().setUserName(update);
            } else if (id == R.id.nav_intro) {
                CurrentAccountController.getCurrAccount().getProfile().setIntro(update);
            } else if (id == R.id.nav_password) {
                CurrentAccountController.getCurrAccount().setPassword(update);
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    currentUser.updatePassword(update);
                }
            }
            updateCurrAccount();
        }
    }

    void updateImage(int id) {
        if (CurrentAccountController.getCurrAccount() != null) {
            CurrentAccountController.getCurrAccount().getProfile().setAvatarId(id);
            updateCurrAccount();
        }
    }

    void updateCurrAccount() {
        CurrentAccountController.writeData(mContext);
    }
}
