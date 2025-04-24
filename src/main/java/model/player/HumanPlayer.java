package player;

import java.util.ArrayList;
import java.util.List;

import main.java.model.deck.Card;

public class HumanPlayer extends Player {
	private List<Card> discards;

	public HumanPlayer(String name) {
		super(name);
		discards = new ArrayList<Card>();
	}

	@Override
	List<Card> selectDiscards() {
		return discards;
	}
	
	public void setDiscards(Card c1, Card c2) {
		discards.add(c1);
		discards.add(c2);
	}
}
