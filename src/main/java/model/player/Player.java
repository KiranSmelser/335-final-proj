package player;

import java.util.List;

import cribbage.Hand;
import deck.Card;

public class Player {
	private final Hand hand = new Hand();

    public void addCard(Card card) {
        hand.addCard(card);
    }

	public List<Card> selectDiscards() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void incrementWins() {
		// TODO Auto-generated method stub
		
	}

	public void incrementLosses() {
		// TODO Auto-generated method stub
		
	}

	public void resetHand() {
		// TODO Auto-generated method stub
		
	}

	public Hand getHand() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Card playCard(List<Card> playedCards, int currentCount) {
        for (Card c : hand.getCards()) {
            if (c.getValue() + currentCount <= 31) {
                return c;
            }
        }
        return null;
	}
}
