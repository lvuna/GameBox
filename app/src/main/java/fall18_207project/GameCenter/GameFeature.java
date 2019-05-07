package fall18_207project.GameCenter;

public interface GameFeature {

    boolean isSolved();

    boolean isValidTap(int position);

    void touchMove(int position);

    long getElapsedTime();
}
