package player;

import java.util.List;

import main.java.model.cribbage.Hand;
import main.java.model.deck.Card;

public abstract class Player {
	private String name;
	private Hand hand;
	private int wins;
	
	public Player(String name) {
		this.name = name;
		hand = new Hand();
		wins = 0;
	}

	// Adds a card to the hand
	public void addCard(Card card) {
        hand.addCard(card);
    }

	// Removes and returns two cards to be discarded from the hand
	abstract List<Card> selectDiscards();
	
	// 
	public Card playCard(List<Card> playedCards, int currentCount) {
        for (Card c : hand.getCards()) {
            if (c.getValue() + currentCount <= 31) {
                return c;
            }
        }
        return null;
	}
	
	public void incrementWins() {
		wins++;
	}
	
	// Sets hand to a new empty hand object
	public void resetHand() {
		hand = new Hand();
	}

	public String getName() {
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public int getWins() {
		return wins;
	}
}
