package player;

import java.util.ArrayList;
import java.util.List;

import cribbage.Board;
import cribbage.Hand;
import deck.Card;

public class HardStrategy implements Strategy {
	
	@Override
	public List<Card> discard(Hand hand, Board board) {
		List<Card> cards = hand.getCards();
		List<Card> bestDiscards = null;
		Card starter = board.getStarter();
		int maxScore = 0;
		
		for (int i = 0; i < cards.size()-1; i++) {
			for (int j = i+1; j < cards.size(); j++) {
				// create a copy of hand object
				Hand handCopy = new Hand();
				for (Card c : cards) {
					handCopy.addCard(c);
				}
				Card discard1 = cards.get(i); Card discard2 = cards.get(j);
				handCopy.discard(discard1); handCopy.discard(discard2);
				int currScore = handCopy.getScore(starter);
				if (currScore > maxScore) {
					maxScore = currScore;
					bestDiscards = new ArrayList<>();
					// store the two cards to discard that leave the best scoring hand
					bestDiscards.add(discard1); bestDiscards.add(discard2);
				}
			}
		}
		return bestDiscards; 
	}
}
