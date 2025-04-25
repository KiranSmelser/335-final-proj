package model.player;

import java.util.List;
import model.cribbage.Hand;
import model.cribbage.Board;
import model.deck.Card;

public interface Strategy {
    List<Card> discard(Hand hand, Board board, Card starter);
    Card playCard(Hand hand, List<Card> playedCards, int currentCount);
}
