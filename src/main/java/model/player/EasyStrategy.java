package player;

import java.util.Collections;
import java.util.List;

import cribbage.Board;
import cribbage.Hand;
import deck.Card;

public class EasyStrategy implements Strategy {

    @Override
    public List<Card> discard(Hand hand, Board board, Card starter) {
        List<Card> cards = hand.getCards();
        Collections.shuffle(cards);
        return cards.subList(0, 2);
    }

}
