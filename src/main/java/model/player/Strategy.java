package player;

import java.util.List;

import cribbage.Board;
import cribbage.Hand;
import deck.Card;

public interface Strategy {
    List<Card> discard(Hand hand, Board board, Card starter);
}
