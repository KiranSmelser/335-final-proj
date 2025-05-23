package model.deck;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private List<Card> cards;

    /* CONSTRUCTORS */
    public Deck() {
        cards = new ArrayList<Card>();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                Card c = Card.get(r, s);
                cards.add(c);
            }
        }
    }
    /* METHODS */

    // Remove card from top of Deck
    public Card pop() {
        return cards.remove(cards.size() - 1);
    }

    // Shuffle deck of Cards
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Reset deck of cards
    public void reset() {
        cards.clear();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                Card c = Card.get(r, s);
                cards.add(c);
            }
        }
    }
}
