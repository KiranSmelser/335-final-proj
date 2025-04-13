package player;

import java.util.Collections;
import java.util.List;

import cribbage.Hand;
import deck.Card;

public class EasyStrategy implements Strategy {

	@Override
	public List<Card> discard(Hand hand, boolean isDealer) {
		List<Card> cards = hand.getCard(); // temp method
		Collections.shuffle(cards);
		return cards.subList(0, 2);
		return null;
	}

}
