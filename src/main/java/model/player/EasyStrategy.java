package model.player;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import model.cribbage.Board;
import model.cribbage.Hand;
import model.deck.Card;

public class EasyStrategy implements Strategy {

    @Override
    public List<Card> discard(Hand hand, Board board, Card starter) {
        List<Card> cards = hand.getCards();
        Collections.shuffle(cards);
        return cards.subList(0, 2);
    }

    @Override
    public Card playCard(Hand hand, List<Card> playedCards, int currentCount) {
        List<Card> playable = new ArrayList<>();
        for (Card c : hand.getCards()) {
            if (c.getValue() + currentCount <= 31) {
                playable.add(c);
            }
        }
        if (playable.isEmpty()) {
            return null;
        }
        Collections.shuffle(playable);
        return playable.get(0);
    }
}
