package fall18_207project.GameCenter;

import android.content.Context;

/*
Taken From
https://www.youtube.com/watch?v=5RVzknsdknw

Date of Retrieval
2018-11-05

This Class is a Timer that counts up by 1 per second.
 */

/**
 * A simple Runnable class to generate time difference since a starting time in milliseconds
 */
public class GameChronometer implements Runnable {
    /***
     *  Some constants for milliseconds to hours, minutes, and seconds conversion
     */

    private static final long MILLIS_TO_MINUTES = 1000 * 60;
    private static final long MILLS_TO_HOURS = 1000 * 60 * 60;

    private Context mContext;
    private long mStartTime;
    private long savedTime;
    private boolean mIsRunning;

    /**
     * Constructor for the class for normal usage
     *
     * @param context the Activity which is responsible fot this insatnce of class
     */
    GameChronometer(Context context) {
        mContext = context;
        mStartTime = System.currentTimeMillis();
        savedTime = System.currentTimeMillis();
    }

    /**
     * Constructor which takes context and also an already set starting time
     * this is used mainly for onResume if the application was stopped for any reason
     *
     * @param context
     * @param startTime
     */
    GameChronometer(Context context, long startTime) {
        this(context);
        mStartTime = startTime;
        savedTime = System.currentTimeMillis();
    }

    /**
     * Starts the chronometer
     */
    public void start() {
        if (mStartTime == 0) { //if the start time was not set before! e.g. by second constructor
            mStartTime = System.currentTimeMillis();
        }
        mIsRunning = true;
    }

    /**
     * Stops the chronometer
     */
    void stop() {
        mIsRunning = false;
    }

    long getElapsedTime() {
        return System.currentTimeMillis() - mStartTime;
    }

    public long getActualElapsedTime() {
        return System.currentTimeMillis() - savedTime;
    }

    public void updateSavedTime() {
        this.savedTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (mIsRunning) {
            //We do not call ConvertTimeToString here because it will add some overhead
            //therefore we do the calculation without any function calls!

            //Here we calculate the difference of starting time and current time
            long since = System.currentTimeMillis() - mStartTime;

            //convert the resulted time difference into hours, minutes, seconds and milliseconds
            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLS_TO_HOURS))); //this does not reset to 0!

            ((GameActivity) mContext).updateTimerText(String.format("%02d:%02d:%02d"
                    , hours, minutes, seconds));

            //Sleep the thread for a short amount, to prevent high CPU usage!
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}