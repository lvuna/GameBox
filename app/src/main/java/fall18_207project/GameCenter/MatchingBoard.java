package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class MatchingBoard extends Observable implements Cloneable, Serializable, Iterable<Card> {

    private int numOfColumns;
    private int numOfRows;
    private Card[][] cards;

    public MatchingBoard(List<Card> cardList, int size) {
        this.numOfColumns = size;
        this.numOfRows = size;
        this.cards = new Card[numOfRows][numOfColumns];

        Iterator<Card> iter = cardList.iterator();

        for (int row = 0; row < this.getNumOfRows(); row++) {
            for (int col = 0; col < this.getNumOfColumns(); col++)
                this.cards[row][col] = iter.next();
        }

    }

    public int getNumOfColumns() {
        return numOfColumns;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    Card getCard(int row, int col) {
        return cards[row][col];
    }


    int getNumOfCards() {
        return numOfColumns * numOfRows;
    }

    void turnCard(int row, int col, boolean up) {
        cards[row][col].turn(up);
        setChanged();
        notifyObservers();
    }

    void useCards(int row, int col, int row1, int col1) {
        cards[row][col].setUsed(true);
        cards[row1][col1].setUsed(true);
    }

    @NonNull
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(cards) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Card> iterator() {
        return new MatchingBoard.BoardIterator();
    }

    /**
     * The iterator class for Board's iterator.
     */
    private class BoardIterator implements Iterator<Card> {
        /**
         * The position
         */
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position != numOfColumns * numOfRows;
        }

        @Override
        public Card next() {
            int row = position / numOfRows;
            int col = position % numOfColumns;
            Card nextCard = cards[row][col];
            position++;
            return nextCard;
        }
    }
}
