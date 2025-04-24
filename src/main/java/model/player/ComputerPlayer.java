package player;

import java.util.List;

import cribbage.Board;
import deck.Card;

public class ComputerPlayer extends Player {
    private Strategy strategy;
    private Board board;

    public ComputerPlayer(String name, Strategy strategy, Board board) {
        super(name);
        this.strategy = strategy;
        this.board = board;
    }

    @Override
    public List<Card> selectDiscards(Card starter) {
        return strategy.discard(getHand(), board, starter);
    }
}
