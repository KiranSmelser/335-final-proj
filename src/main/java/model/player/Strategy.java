package player;

import java.util.List;
import cribbage.Hand;
import cribbage.Board;
import deck.Card;

public interface Strategy {
    List<Card> discard(Hand hand, Board board, Card starter);
    Card playCard(Hand hand, List<Card> playedCards, int currentCount);
}
