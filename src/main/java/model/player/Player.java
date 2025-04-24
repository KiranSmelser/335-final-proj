package player;

import java.util.List;

import cribbage.Hand;
import deck.Card;

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

	// Returns two cards to be discarded from the hand
	public abstract List<Card> selectDiscards(Card Starter);
	
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

	public void incrementLosses() {
		// TODO Auto-generated method stub
		
	}
}
