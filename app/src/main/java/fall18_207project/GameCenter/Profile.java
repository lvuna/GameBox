package fall18_207project.GameCenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.Serializable;

class Profile implements Serializable {
    /***
     * A profile class for account.
     */

    // Avatar image location.
    private int avatarId;
    // Self description.
    private String intro;
    // Does not count unfinished game.
    private long totalPlayTime;

    Profile() {
        this.avatarId = R.drawable.paulorange1;
        this.intro = "A New user who hasn't set his intro.";
        this.totalPlayTime = 0;
    }


    public int getAvatarId() {
        return this.avatarId;
    }

    void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getIntro() {
        return intro;
    }

    void setIntro(String intro) {
        this.intro = intro;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    void updateTotalPlayTime(long newPlayTime) {
        this.totalPlayTime += newPlayTime;
    }

    public String getDisplayTime(){
        long convertToSeconds = getTotalPlayTime() / 1000;
        long seconds = convertToSeconds % 60;
        long minutes = convertToSeconds / 60;
        long hours = convertToSeconds / 2400;
        String displayTime = hours + "hrs" + minutes + "min" + seconds + "sec";
        return displayTime;
    }
}
