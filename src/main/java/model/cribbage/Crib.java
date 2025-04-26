package model.cribbage;

import java.util.List;
import java.util.ArrayList;
import model.deck.Card;

public class Crib {
    private final List<Card> cards = new ArrayList<>();

    public void addCards(List<Card> discards) {
        cards.addAll(discards);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public void clear() {
        cards.clear();
    }

    public int score(Card starter) {
        Hand cribHand = new Hand();
        for (Card c : cards) {
            cribHand.addCard(c);
        }

        int flush  = cribHand.getFlushSum(starter);
        int points = cribHand.getScore(starter);

        if (flush == 4) {
            points -= flush;
        }
        return points;
    }
}