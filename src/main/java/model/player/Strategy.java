package player;

import java.util.List;

import cribbage.Hand;
import deck.Card;

public interface Strategy {
    List<Card> discard(Hand hand, boolean isDealer);
}
