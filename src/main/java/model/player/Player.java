package model.player;

import java.util.List;

import model.cribbage.Hand;
import model.deck.Card;

public abstract class Player {
	private String name;
	private Hand hand;
	private int wins;
	
	public Player(String name) {
		this.name = name;
		hand = new Hand();
		wins = 0;
	}

	public void addCard(Card card) {
        	hand.addCard(card);
    	}

	public void sortCards() {
		hand.sort();
	}

	public abstract List<Card> selectDiscards(Card Starter);
	
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
	
	public void resetHand() {
		hand = new Hand();
	}

	public String getName() {
		return name;
	}
	
	public Hand getHand() {
		Hand handCopy = new Hand();
		for (Card c : hand.getCards()) {
			handCopy.addCard(c);
		}
		
		return hand;
	}
	
	public int getWins() {
		return wins;
	}

}
