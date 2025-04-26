package model.player;

import java.util.List;

import model.cribbage.Board;
import model.deck.Card;

public class ComputerPlayer extends Player {
    private Strategy strategy;
    private Board board;

    public ComputerPlayer(String name, Strategy strategy, Board board) {
        super(name);
        this.strategy = strategy;
        this.board = board;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public List<Card> selectDiscards(Card starter) {
        return strategy.discard(getHand(), board, starter);
    }
    
    @Override
    public Card playCard(List<Card> playedCards, int currentCount) {
        return strategy.playCard(getHand(), playedCards, currentCount);
    }

	public Strategy getStrategy() {
		return strategy;
	}
}
