package player;

import java.util.List;

import main.java.model.cribbage.Board;
import main.java.model.deck.Card;

public class ComputerPlayer extends Player {
	private Strategy strategy;
	private Board board;

	public ComputerPlayer(String name, Strategy strategy, Board board) {
		super(name);
		this.strategy = strategy;
		this.board = board;
	}

	@Override
	List<Card> selectDiscards() {
		return strategy.discard(getHand(), board);
	}
}
