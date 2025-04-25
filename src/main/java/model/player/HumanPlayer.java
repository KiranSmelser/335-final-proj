package model.player;

import java.util.ArrayList;
import java.util.List;

import model.deck.Card;
import view.View;

public class HumanPlayer extends Player {
	private List<Card> discards;
	private View view;

	public HumanPlayer(String name, View view) {
		super(name);
		this.view = view;
		discards = new ArrayList<Card>();
	}

	@Override
	public List<Card> selectDiscards(Card starter) {
		discards = view.promptDiscards(this, starter);
		return discards;
	}
	
	public void setDiscards(Card c1, Card c2) {
		discards.add(c1);
		discards.add(c2);
	}
}
