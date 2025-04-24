package player;

import java.util.ArrayList;
import java.util.List;

import deck.Card;

public class HumanPlayer extends Player {
	private List<Card> discards;

	public HumanPlayer(String name) {
		super(name);
		discards = new ArrayList<Card>();
	}

	@Override
	public List<Card> selectDiscards(Card starter) {
		return discards;
	}
	
	public void setDiscards(Card c1, Card c2) {
		discards.add(c1);
		discards.add(c2);
	}
}
