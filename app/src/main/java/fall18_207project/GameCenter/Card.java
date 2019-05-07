package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * The card in matching card game
 */
public class Card implements Comparable<Card>, Serializable {
    /**
     * The id of the card
     */
    private int id;
    /**
     * The background of the card
     */
    private int background;

    private boolean up;
    private boolean used;
    private boolean bomb;

    public int getId() {
        return id;
    }

    public int getBackground() {
        return background;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isBomb() {
        return bomb;
    }

    Card(int position) {
        id = position + 1;
        up = false;
        used = false;
        bomb = false;
        switch (position + 1) {
            case 1:
                background = R.drawable.p1;
                break;
            case 2:
                background = R.drawable.p2;
                break;
            case 3:
                background = R.drawable.p3;
                break;
            case 4:
                background = R.drawable.p4;
                break;
            case 5:
                background = R.drawable.p5;
                break;
            case 6:
                background = R.drawable.p6;
                break;
            case 7:
                background = R.drawable.p7;
                break;
            case 8:
                background = R.drawable.p8;
                break;
            case 9:
                background = R.drawable.p9;
                break;
            case 10:
                background = R.drawable.p10;
                break;
            case 11:
                background = R.drawable.p11;
                break;
            case 12:
                background = R.drawable.p12;
                break;
            case 13:
                background = R.drawable.p13;
                break;
            case 14:
                background = R.drawable.p14;
                break;
            case 15:
                background = R.drawable.p15;
                break;
            case 16:
                background = R.drawable.p16;
                break;
            case 17:
                background = R.drawable.p17;
                break;
            case 18:
                background = R.drawable.p18;
                break;
            case 19:
                background = R.drawable.p19;
                break;
            case 20:
                background = R.drawable.p20;
                break;
            case 21:
                background = R.drawable.p21;
                break;
            case 22:
                background = R.drawable.p22;
                break;
            case 23:
                background = R.drawable.p23;
                break;
            case 24:
                background = R.drawable.p24;
                break;
            case 27:
                background = R.drawable.p27;
                break;
            case 28:
                background = R.drawable.p28;
                break;
            case 29:
                background = R.drawable.p29;
                break;
            case 30:
                background = R.drawable.p30;
                break;
            default:
                background = R.drawable.bomb;
                bomb = true;
        }
    }

    public void turn(boolean face) {
        up = face;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}
